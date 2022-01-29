package unitwifi.com.artikle;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends Fragment{

    private EditText titleInput;
    private Spinner categoryInput;
    private Button search;

    public static Search newInstance() {
        Search fragment = new Search();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceBundle){

        titleInput = (EditText) view.findViewById(R.id.titleInput);
        categoryInput = (Spinner) view.findViewById(R.id.categoryInput);

        search = (Button) view.findViewById(R.id.search);

        String [] strings = getResources().getStringArray(R.array.category);
        List<String> stringList = new ArrayList<String>(Arrays.asList(strings));

        stringList.add(0, "Select a category");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, stringList);

        categoryInput.setAdapter(spinnerAdapter);

        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String category = categoryInput.getSelectedItem().toString();
                if(!title.equals("")){
                    if(category.equals("Select a category")){
                        Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            searchFunc(title, category);
                        } catch (XmlPullParserException xml){
                            xml.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });*/
    }

    public void searchFunc(String title, String category) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        String path = "article_xml/articles.xml";
        InputStream stream = getActivity().getAssets().open(path);
        xpp.setInput(stream, "utf-8");
        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String strName = xpp.getName();
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                /*if(xpp.getAttributeValue("title", null).equals(title)){
                    Toast.makeText(getContext(), "found title", Toast.LENGTH_SHORT).show();
                    if(category.equals(xpp.getAttributeValue("category", null))){
                        Toast.makeText(getContext(), "found", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }else if(eventType == XmlPullParser.END_DOCUMENT){
                break;
            }
            eventType = xpp.next();
        }
    }

}
