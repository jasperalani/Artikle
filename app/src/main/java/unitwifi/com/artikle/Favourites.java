package unitwifi.com.artikle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Favourites extends Fragment {

    private ListView listViewFavourites;
    private int favouritesCount = 0;
    private List<String> articles = new ArrayList<>();

    public static Favourites newInstance() {
        Favourites fragment = new Favourites();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceBundle){
        Home home = new Home();

        listViewFavourites = (ListView) view.findViewById(R.id.listViewFavourites);

        final String PREFS_NAME = "favourites";
        SharedPreferences favouritesPrefs = getActivity().getSharedPreferences(PREFS_NAME, 0);
        favouritesCount = favouritesPrefs.getInt("savedArticles", 0);

        Intent extras = getActivity().getIntent();

        if(extras.hasExtra("remove_id")){
            if(articles.size() > 0){
                articles.remove(extras.getIntExtra("remove_id", 1));
            }
            favouritesPrefs.edit().putInt("savedArticles", favouritesCount - 1).commit();
            extras.removeExtra("remove_id");
        }

        if(extras.hasExtra("item")){
            favouritesPrefs.edit().putString("item" + String.valueOf(favouritesCount), extras.getStringExtra("item")).commit();
            favouritesPrefs.edit().putInt("savedArticles", favouritesCount + 1).commit();
            extras.removeExtra("item");
        }

        favouritesCount = favouritesPrefs.getInt("savedArticles", 0);

        if(favouritesCount > 0){
            for(int i = 0; i < favouritesCount; i++){
                articles.add(favouritesPrefs.getString("item" + i, ""));
            }
        }

        HashMap<String, String> hashMap ;
        ArrayList<HashMap<String, String>> Articles = new ArrayList<HashMap<String,String>>();
        if(articles.size() > 0){
            for (int i = 0; i < articles.size(); i++) {
                hashMap = new HashMap<String, String>();
                hashMap.put("title", home.getTitle(articles.get(i)));
                hashMap.put("category", home.getCategory(articles.get(i)));
                Articles.add(hashMap);
            }
        }

        ListAdapter adapter = new SimpleAdapter(view.getContext(), Articles, R.layout.listitem_layout, new String[] {"title", "category"}, new int[] {R.id.listItemTitle, R.id.listItemCategory});
        listViewFavourites.setAdapter(adapter);

        listViewFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Home home = new Home();
                Intent viewArticle = new Intent(getContext(), ViewArticle.class);
                viewArticle.putExtra("article_id", position);
                viewArticle.putExtra("article_title", home.getTitle(articles.get(position)));
                viewArticle.putExtra("article_cat", home.getCategory(articles.get(position)));
                viewArticle.putExtra("favourites", true);
                startActivity(viewArticle);
            }
        });

        /*listViewFavourites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                articles.remove(position);
                return false;
            }
        });*/

    }

}
