package multilangyuage.project.com.moviestage_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import multilangyuage.project.com.moviestage_1.MovieContract.MovieEntry;

/**
 * Created by Abhishek on 20/07/2017.
 */

public class MovieHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
                + MovieEntry._ID + " INTEGER NOT NULL PRIMARY KEY, "
                + MovieEntry.MOVIE_ID + "  TEXT , "
                + MovieEntry.MOVIE_NAME + " TEXT, "
                + MovieEntry.MOVIE_REVIEW_URL + " TEXT, "
                + MovieEntry.MOVIE_REVIEW + " TEXT, "
                + MovieEntry.MOVIE_FAV + " TEXT "

                + ");";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
