package unitwifi.com.artikle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextPage extends AppCompatActivity {

    private TextView title, text;
    private Button email, phone;
    private LinearLayout contactLayout;
    private boolean contact = false;

    private int PERMISSIONS_REQUEST_CALL_PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_page);

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.textView);

        contactLayout = (LinearLayout) findViewById(R.id.contact);
        email = (Button) findViewById(R.id.email);
        phone = (Button) findViewById(R.id.phone);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            title.setText(extras.getString("title"));
            text.setText(extras.getString("text"));
            contact = extras.getBoolean("contact", false);
            if(contact){
                contactLayout.setVisibility(View.VISIBLE);
            }
        }

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact){
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","help@unitwifi.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contact){
                    int permissionCheckPhone = ContextCompat.checkSelfPermission(TextPage.this,
                            Manifest.permission.CALL_PHONE);
                    if(permissionCheckPhone == PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0221513985"));
                        startActivity(intent);
                    }else{
                        ActivityCompat.requestPermissions(TextPage.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                }
            }
        });
    }
}
