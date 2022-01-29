package unitwifi.com.artikle;

import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends Fragment {

    private ListView listViewArticles;
    private String items[];

    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        listViewArticles = (ListView) view.findViewById(R.id.listViewArticles);
        items = getResources().getStringArray(R.array.listViewArticlesTitles);

        HashMap<String, String> hashMap ;
        ArrayList<HashMap<String, String>> Articles = new ArrayList<HashMap<String,String>>();
        for (int i = 0; i < items.length; i++) {
            hashMap = new HashMap<String, String>();
            hashMap.put("title", getTitle(items[i]));
            hashMap.put("category", getCategory(items[i]));
            Articles.add(hashMap);
        }

        ListAdapter adapter = new SimpleAdapter(view.getContext(), Articles, R.layout.listitem_layout, new String[] {"title", "category"}, new int[] {R.id.listItemTitle, R.id.listItemCategory});
        listViewArticles.setAdapter(adapter);

        listViewArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewArticle = new Intent(getContext(), ViewArticle.class);
                viewArticle.putExtra("article_id", position);
                viewArticle.putExtra("article_title", getTitle(items[position]));
                viewArticle.putExtra("article_cat", getCategory(items[position]));
                viewArticle.putExtra("item", items[position]);
                startActivity(viewArticle);
            }
        });
    }

    public String getTitle(String title){
        return title.substring(0, title.length() - 1);
    }

    public String getCategory(String title){
        String category = title.substring(title.length() - 1);
        int category_ = Integer.parseInt(category);
        switch (category_){
            case 1:
                return "Education";
            case 2:
                return "Sports";
            case 3:
                return "Health";
            case 4:
                return "Gaming";
            case 5:
                return "News";
            case 6:
                return "Finance";
            case 7:
                return "Travel";
            case 8:
                return "Politics";
            case 9:
                return "Food";
            default:
                return "";
        }
    }

}
