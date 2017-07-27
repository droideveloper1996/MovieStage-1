package multilangyuage.project.com.moviestage_1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import multilangyuage.project.com.moviestage_1.MovieContract.MovieEntry;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity.this";
    public static String REVIEW_URL;
    String id;
    ImageView trailer1;
    ImageView trailer2;
    String key = "";
    String reviewId = "";
    ImageView mFavourite;
    String cont = "";
    String MOVIE_NME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView movieDetail = (TextView) findViewById(R.id.movie_detail);
        TextView rating = (TextView) findViewById(R.id.movie_rating);
        TextView language = (TextView) findViewById(R.id.movie_language);
        TextView adult = (TextView) findViewById(R.id.movie_adult);
        mFavourite = (ImageView) findViewById(R.id.favourite);
        TextView title = (TextView) findViewById(R.id.movie_name);
        TextView vote = (TextView) findViewById(R.id.vote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        if (i != null) {

            if (i.hasExtra("url")) {
                String url = i.getStringExtra("url");
                Log.i(TAG, "URL " + url);
                Picasso.with(DetailActivity.this).load(url).into((ImageView) findViewById(R.id.movie_poster));
            }
            if (i.hasExtra("title") || i.hasExtra("original_title")
                    || i.hasExtra("adult") || i.hasExtra("release")
                    || i.hasExtra("popularity") || i.hasExtra("overview")
                    || i.hasExtra("language")) {
                title.setText(i.getStringExtra("title"));
                rating.setText("Popularity - " + i.getStringExtra("popularity"));
                language.setText("Language - " + i.getStringExtra("language"));
                movieDetail.setText("Release  " + i.getStringExtra("release") + '\n' + i.getStringExtra("overview"));
                vote.setText("Vote - " + i.getStringExtra("vote"));
                adult.setText("Adult - " + i.getStringExtra("adult"));
                MOVIE_NME = i.getStringExtra("title");

            }

            REVIEW_URL = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=71a04d355457ca35979a23b0c4a30714";
        }
        trailer1 = (ImageView) findViewById(R.id.trailer1);
        trailer2 = (ImageView) findViewById(R.id.trailer2);

        trailer1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer(key);
            }
        });

        trailer2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer(key);
            }
        });
        final String VIDEO_URL = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=71a04d355457ca35979a23b0c4a30714";
        System.out.println(VIDEO_URL);
        new Trailer().execute(VIDEO_URL);

        getReview(REVIEW_URL);


        mFavourite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavourite.setImageResource(R.drawable.ic_favorite_black_48px);
                Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public void playTrailer(String key1) {
        Intent i = new Intent(this, YoutubePlayerActivity.class);
        i.setAction(key1);
        startActivity(i);
    }

    public void getReview(String url) {
        URL review_url = NetworkUtils.makeUrl(url);
        new Trailer().execute(review_url.toString());
    }

    public void addToFavourite(String review) {


        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();


        contentValues.put(MovieEntry.MOVIE_ID, id);
        contentValues.put(MovieEntry.MOVIE_NAME, MOVIE_NME);
        contentValues.put(MovieEntry.MOVIE_FAV, "1");
        contentValues.put(MovieEntry.MOVIE_REVIEW_URL, REVIEW_URL);
        contentValues.put(MovieEntry.MOVIE_REVIEW, review);


        Uri uri = contentResolver.insert(MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(DetailActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
        }


        getResult();

    }

    public void getResult() {
        Cursor cursor = null;
        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int movie_name = cursor.getColumnIndex(MovieEntry.MOVIE_NAME);
                int movie_id = cursor.getColumnIndex(MovieEntry.MOVIE_ID);
                int movie_url = cursor.getColumnIndex(MovieEntry.MOVIE_REVIEW_URL);
                int movie_review = cursor.getColumnIndex(MovieEntry.MOVIE_REVIEW);
                int mov_fav = cursor.getColumnIndex(MovieEntry.MOVIE_FAV);


                Log.i("Movie Details",

                        cursor.getString(movie_name) + "      " +
                                cursor.getString(movie_id) + "        " +
                                cursor.getString(movie_url) + "       " +
                                cursor.getString(movie_review) + "        " +
                                cursor.getString(mov_fav)
                );
            }
        }
    }

    public class Trailer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String Json = "";
            Log.i("params[0]", params[0]);
            if (params[0] == null) {
                return Json;
            }
            URL url = NetworkUtils.makeUrl(params[0]);
            try {
                Json = NetworkUtils.makeHttpConnectionKey(url);
                System.out.println(Json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                Log.i("Response", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.optJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        key = object.optString("key");
                        reviewId = object.optString("id");
                        cont += object.optString("content");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            addToFavourite(cont);
        }
    }


}
