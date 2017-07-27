package multilangyuage.project.com.moviestage_1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import multilangyuage.project.com.moviestage_1.MovieContract.MovieEntry;

/**
 * Created by Abhishek on 20/07/2017.
 */

public class MovieProvider extends ContentProvider {

    public static final int MATCH_WITH_ID = 101;
    public static final int MATCH_WITHOUT_ID = 100;
    public static final UriMatcher sUrimatcher = getmatch();
    MovieHelper movieHelper;

    public static UriMatcher getmatch() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH, MATCH_WITHOUT_ID);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH + "/#", MATCH_WITHOUT_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = movieHelper.getReadableDatabase();

        Cursor cursor = null;
        int match = sUrimatcher.match(uri);

        switch (match) {
            case MATCH_WITHOUT_ID:
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);

                break;
            case MATCH_WITH_ID:
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);

                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUrimatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieHelper.getWritableDatabase();
        Uri appendedUri = null;
        long row;
        switch (match) {
            case MATCH_WITHOUT_ID:
                row = sqLiteDatabase.insert(MovieEntry.TABLE_NAME, null, values);
                if (row > 0) {
                    appendedUri = ContentUris.withAppendedId(uri, row);
                }
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return appendedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = movieHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        int rowsDeleted = -1;
        switch (match) {
            case MATCH_WITH_ID:
                rowsDeleted = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MATCH_WITHOUT_ID:
                rowsDeleted = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, null, null);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
