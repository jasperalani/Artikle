package unitwifi.com.artikle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView title, subtitle;
    private Button startReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);

        startReading = (Button) findViewById(R.id.startReading);
        startReading.setOnClickListener(this);

        Typeface segoeui = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");
        title.setTypeface(segoeui);
        subtitle.setTypeface(segoeui);

        final String PREFS_NAME = "SystemPrefs";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("FIRST_RUN", true)) {
            Log.d("Comments", "First time");
            settings.edit().putBoolean("FIRST_RUN", false).commit();
        }else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.startReading:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }
}
