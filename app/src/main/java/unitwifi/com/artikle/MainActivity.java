package unitwifi.com.artikle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = Menu.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = Home.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = Favourites.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = Search.newInstance();
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Intent intent = getIntent();
        if(intent.hasExtra("fragment")){
            if(intent.getStringExtra("fragment").equals("favourites")){
                transaction.replace(R.id.frame_layout, Favourites.newInstance()).commit();
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
            }
        }else{
            transaction.replace(R.id.frame_layout, Home.newInstance()).commit();
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
    }
}
