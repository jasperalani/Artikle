package unitwifi.com.artikle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Menu extends Fragment {

    private ListView options;
    private String[] menuOptions;

    public static Menu newInstance() {
        Menu fragment = new Menu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceBundle){
        super.onViewCreated(view, savedInstanceBundle);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                getActivity().findViewById(R.id.navigation);

        options = (ListView) view.findViewById(R.id.options);
        menuOptions = getResources().getStringArray(R.array.menuOptions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.settings_layout, R.id.settingsItemTitle, menuOptions);
        options.setAdapter(adapter);

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent about = new Intent(getContext(), TextPage.class);
                        about.putExtra("title", getResources().getString(R.string.about));
                        about.putExtra("text", getResources().getString(R.string.aboutText));
                        startActivity(about);
                        break;
                    case 1:
                        Intent contact = new Intent(getContext(), TextPage.class);
                        contact.putExtra("title", getResources().getString(R.string.contact));
                        contact.putExtra("text", getResources().getString(R.string.contactText));
                        contact.putExtra("contact", true);
                        startActivity(contact);
                        break;
                    case 2:
                        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                        transaction1.replace(R.id.frame_layout, Favourites.newInstance());
                        transaction1.commit();
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                        transaction2.replace(R.id.frame_layout, Search.newInstance());
                        transaction2.commit();
                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        break;
                    case 4:
                        Intent usrAg = new Intent(getContext(), TextPage.class);
                        usrAg.putExtra("title", getResources().getString(R.string.useragreement));
                        usrAg.putExtra("text", getResources().getString(R.string.useragreementText));
                        startActivity(usrAg);
                        break;
                    case 5:
                        Intent tnc = new Intent(getContext(), TextPage.class);
                        tnc.putExtra("title", getResources().getString(R.string.termsncons));
                        tnc.putExtra("text", getResources().getString(R.string.termsnconsText));
                        startActivity(tnc);
                        break;
                    case 6:
                        Intent pp = new Intent(getContext(), TextPage.class);
                        pp.putExtra("title", getResources().getString(R.string.privacypol));
                        pp.putExtra("text", getResources().getString(R.string.privacypolText));
                        startActivity(pp);
                        break;
                }
            }
        });
    }
}
