package multilangyuage.project.com.moviestage_1;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Abhishek on 20/07/2017.
 */

public class MovieContract {


    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "multilangyuage.project.com.moviestage";
    public static final String PATH = "movie";
    public static final Uri BASE_URI = Uri.parse(SCHEME + AUTHORITY);

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
        public static final String TABLE_NAME = "movie";
        public static final String MOVIE_NAME = "movie_name";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_REVIEW_URL = "movie_url";
        public static final String MOVIE_REVIEW = "review";
        public static final String MOVIE_FAV = "favourite";

    }
}
