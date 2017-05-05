package multilangyuage.project.com.moviestage_1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=71a04d355457ca35979a23b0c4a30714";
    public static final String TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=71a04d355457ca35979a23b0c4a30714";
    List<URL> urllist;
    ProgressBar progressBar;
    ImageView imageView;
    TextView textView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        textView=(TextView)findViewById(R.id.internet);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (isOnline()) {
            FetchData task = new FetchData();
            task.execute(POPULAR_URL);
            imageView = (ImageView) findViewById(R.id.imageView);


        } else {
            imageView = (ImageView) findViewById(R.id.imageView);
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.warning);
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

        }

    }

    public class FetchData extends AsyncTask<String, Void, List<URL>> {


        @Override
        protected void onPostExecute(List<URL> list) {
            super.onPostExecute(list);
            Log.i("List url", String.valueOf(list.get(0)));
            Context context = MainActivity.this;
            progressBar.setVisibility(View.INVISIBLE);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            CustomAdapter adapter = new CustomAdapter(MainActivity.this, list);
            int numberofColumn = 2;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberofColumn);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected List<URL> doInBackground(String... params) {

            urllist = NetworkUtils.fetchData(params[0]);


            return urllist;
        }

    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
