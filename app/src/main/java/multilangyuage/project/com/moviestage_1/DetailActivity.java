package multilangyuage.project.com.moviestage_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity.this";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView movieDetail = (TextView) findViewById(R.id.movie_detail);
        TextView rating = (TextView) findViewById(R.id.movie_rating);
        TextView language = (TextView) findViewById(R.id.movie_language);
        TextView adult = (TextView) findViewById(R.id.movie_adult);
        TextView title = (TextView) findViewById(R.id.movie_name);
        TextView vote = (TextView) findViewById(R.id.vote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
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

            }
        }
    }
}
