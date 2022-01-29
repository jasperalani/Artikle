package unitwifi.com.artikle;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class ViewArticle extends AppCompatActivity {

    private static final String DEBUG_TAG = "ViewArticle Log";

    private ImageView articleImage;
    private TextView articleTitle, articleCategory, articleText;
    private XmlPullParser articleInfo;

    private Button share, addToFavourites;

    private int articleID;
    private String str_articleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        articleImage = (ImageView) findViewById(R.id.titleImage);
        articleTitle = (TextView) findViewById(R.id.articleTitle);
        articleCategory = (TextView) findViewById(R.id.articleCategory);
        articleText = (TextView) findViewById(R.id.articleText);

        share = (Button) findViewById(R.id.share);
        addToFavourites = (Button) findViewById(R.id.add_to_favourites);

        Intent intent = getIntent();
        articleID = intent.getIntExtra("article_id", 1);
        str_articleID = String.valueOf(articleID);
        articleTitle.setText(str_articleID);
        articleImage.setImageDrawable(getArticleImage());

        final boolean fromFavs = intent.getBooleanExtra("favourites", false);
        if(fromFavs){
            addToFavourites.setText("Remove from Favourites");
        }

        final String articleUnformatted = intent.getStringExtra("item");

        try {
            processData();
        } catch (Exception e){
            Log.e(DEBUG_TAG, "Failed to load article information: ", e);
            articleTitle.setText(intent.getStringExtra("article_title"));
            articleCategory.setText(intent.getStringExtra("article_cat"));
            articleText.setText("Article text failed to load.");
        }

        addToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fromFavs){
                    Intent favourites = new Intent(getApplicationContext(), MainActivity.class);
                    favourites.putExtra("fragment", "favourites");
                    favourites.putExtra("item", articleUnformatted);
                    startActivity(favourites);
                }else{
                    Intent remove = new Intent(getApplicationContext(), MainActivity.class);
                    remove.putExtra("fragment", "favourites");
                    remove.putExtra("remove_id", articleID);
                    startActivity(remove);
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, articleTitle.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_TEXT, articleText.getText().toString());
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

    }

    private void processData()
        throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        String path = "article_xml/article" + str_articleID + ".xml";
        InputStream stream = getAssets().open(path);

        xpp.setInput(stream, "utf-8");

        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String strName = xpp.getName();
                if(strName.equals("Description")){
                    articleTitle.setText(xpp.getAttributeValue(null, "title"));
                    articleCategory.setText(xpp.getAttributeValue(null, "category"));
                    articleText.setText(xpp.getAttributeValue(null, "text"));
                }
            }else if(eventType == XmlPullParser.END_DOCUMENT){
                break;
            }
            eventType = xpp.next();
        }
    }

    private Drawable getArticleImage(){
        String path = "article_images/" + str_articleID + ".png";
        if(articleID == 0){
            path = "article_images/first.PNG";
        }
        try {
            InputStream stream = getAssets().open(path);
            Drawable d = Drawable.createFromStream(stream, null);
            return d;
        } catch (IOException e) {
            e.printStackTrace();
            return new Drawable() {
                @Override
                public void draw(@NonNull Canvas canvas) {

                }

                @Override
                public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

                }

                @Override
                public void setColorFilter(@Nullable ColorFilter colorFilter) {

                }

                @Override
                public int getOpacity() {
                    return PixelFormat.OPAQUE;
                }
            };
        }
    }
}
