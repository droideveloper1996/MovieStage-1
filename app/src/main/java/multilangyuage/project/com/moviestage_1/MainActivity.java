package multilangyuage.project.com.moviestage_1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import multilangyuage.project.com.moviestage_1.CustomAdapter.ListItemClickListner;

public class MainActivity extends AppCompatActivity {
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=71a04d355457ca35979a23b0c4a30714&page=3";
    public static final String TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=71a04d355457ca35979a23b0c4a30714&page=5";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<URL> urllist;
    ProgressBar progressBar;
    ImageView imageView;
    TextView textView;
    RelativeLayout relativeLayout;
    Toast mToast = null;
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean loading = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        textView = (TextView) findViewById(R.id.internet);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (isOnline()) {
            FetchData task = new FetchData();
            task.execute(TOP_RATED_URL);
            imageView = (ImageView) findViewById(R.id.imageView);


        } else {
            setErrorMessage();

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {


            @Override
            public void onRefresh() {
                if (isOnline()) {
                    FetchData task = new FetchData();
                    task.execute(TOP_RATED_URL);
                    swipeRefreshLayout.setRefreshing(true);
                } else {
                    setErrorMessage();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void setErrorMessage() {
        Context context = MainActivity.this;
        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setImageResource(R.drawable.warning);
        imageView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Please Check Internet Connectivity", Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FetchData task = new FetchData();
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        switch (id) {
            case R.id.action_popular:
                if (isOnline()) {
                    task.execute(POPULAR_URL);
                } else {
                    setErrorMessage();
                }
                return true;
            case R.id.action_top:
                if (isOnline()) {
                    task.execute(TOP_RATED_URL);
                } else {
                    setErrorMessage();
                }
                return true;
            default:
                return true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    public class FetchData extends AsyncTask<String, Void, List<URL>> implements ListItemClickListner {


        @Override
        protected void onPostExecute(List<URL> list) {
            super.onPostExecute(list);

            Context context = MainActivity.this;
            if (list != null) {
                swipeRefreshLayout.setRefreshing(false);
                urllist = list;
                progressBar.setVisibility(View.INVISIBLE);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, list, this);
                int numberofColumn = 2;
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberofColumn);



                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

        }

        @Override
        protected List<URL> doInBackground(String... params) {
            urllist = NetworkUtils.fetchData(params[0]);
            return urllist;
        }

        @Override
        public void onClick(int clickedPosition) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(MainActivity.this, String.valueOf(clickedPosition), Toast.LENGTH_SHORT);
            //mToast.show();
            List<Movie> movies = new ArrayList<>();
            movies = NetworkUtils.getMovieDetail();
            Log.i("Movie Detail", movies.get(clickedPosition).getmMovieTitle());
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("url", String.valueOf(urllist.get(clickedPosition)));
            i.putExtra("title", String.valueOf(movies.get(clickedPosition).getmMovieTitle()));
            i.putExtra("original_title", String.valueOf(movies.get(clickedPosition).getmOriginalTitle()));
            i.putExtra("adult", String.valueOf(movies.get(clickedPosition).getmAdult()));
            i.putExtra("release", String.valueOf(movies.get(clickedPosition).getMrelease()));
            i.putExtra("popularity", String.valueOf(movies.get(clickedPosition).getmPopularity()));
            i.putExtra("language", String.valueOf(movies.get(clickedPosition).getmLanguage()));
            i.putExtra("overview", String.valueOf(movies.get(clickedPosition).getmOverview()));
            i.putExtra("vote", String.valueOf(movies.get(clickedPosition).getmVote()));
            i.putExtra("id", String.valueOf(movies.get(clickedPosition).getmId()));
            startActivity(i);

        }

    }

}
