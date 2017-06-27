package multilangyuage.project.com.moviestage_1;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 04/05/2017.
 */

public class NetworkUtils {

    public static final String TAG = "NetworkUtils.class";
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=71a04d355457ca35979a23b0c4a30714";
    public static final String TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=71a04d355457ca35979a23b0c4a30714";
    public static final String imageBaseURL = "https://image.tmdb.org/t/p/w500//";

    public static final String MOVIE_TRAILER = "http://api.themoviedb.org/3/movie/157336/videos?api_key=71a04d355457ca35979a23b0c4a30714";

    static List<Movie> list;

    public NetworkUtils() {
    }

    public static URL makeUrl(String s) {
        URL url = null;
        if (s == null) {
            return url;
        } else
            try {
                url = new URL(s);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return url;
    }

    public static String makeHttpConnection(URL url) throws IOException {
        String jsonResonse = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        if (url == null) {
            return jsonResonse;
        } else {
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(1000/*time in ms*/);
                httpURLConnection.setConnectTimeout(1000/*time in ms*/);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    jsonResonse = decodeInputStream(inputStream);
                } else {
                    Log.i(TAG, "Connection Error " + httpURLConnection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return jsonResonse;
    }

    private static String decodeInputStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        Log.i(TAG, builder.toString());
        extractFeatureFromJson(builder.toString());
        return builder.toString();
    }

    private static ArrayList<URL> extractFeatureFromJson(String s) {

        Log.i(TAG, s);
        ArrayList<URL> urlArrayList = new ArrayList<>();
        list = new ArrayList<Movie>();
        try {
            JSONObject rootJsonObject = new JSONObject(s);
            JSONArray jsonArray = rootJsonObject.optJSONArray("results");
            Log.i(TAG, String.valueOf(jsonArray.length()));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                String pp = jsonObject.optString("poster_path");
                String posterPath = pp.substring(1);
                String id = jsonObject.optString("id");
                String adult = jsonObject.optString("adult");
                String overview = jsonObject.optString("overview");
                String release = jsonObject.optString("release_date");
                String original_title = jsonObject.getString("original_title");
                String language = jsonObject.optString("original_language");
                String title = jsonObject.getString("title");
                String vote = jsonObject.optString("vote_average");
                String populartity = jsonObject.optString("popularity");
                Log.i(TAG, posterPath +
                        '\n' + populartity +
                        '\n' + adult +
                        '\n' + original_title +
                        '\n' + overview +
                        '\n' + release +
                        '\n' + language +
                        '\n' + title +
                        '\n' + id +
                        '\n' + vote);
                URL url = imageURL(posterPath);
                urlArrayList.add(url);
                Movie movie = new Movie(title, adult, overview, release, original_title, language, title, vote, populartity, id);

                list.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return urlArrayList;
    }

    public static URL imageURL(String string) {

        URL imageURL = null;
        if (string != null) {
            Uri uri = Uri.parse(imageBaseURL).buildUpon()
                    .appendPath(string).build();
            Log.i(TAG, "AppendedURL " + uri.toString());
            try {
                imageURL = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return imageURL;
    }

    public static List<URL> fetchData(String requestUrl) {
        Log.i("started", "fetchBooksData()");
        URL url = makeUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpConnection(url);
        } catch (IOException e) {
            Log.e("Problem ", e.toString());
        }
        List<URL> urls = extractFeatureFromJson(jsonResponse);
        return urls;
    }

    public static List<Movie> getMovieDetail() {

        return list;
    }


    public static String makeHttpConnectionKey(URL url) throws IOException {
        String jsonResonse = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        if (url == null) {
            return jsonResonse;
        } else {
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(1000/*time in ms*/);
                httpURLConnection.setConnectTimeout(1000/*time in ms*/);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    jsonResonse = decodeInputStreamKey(inputStream);
                } else {
                    Log.i(TAG, "Connection Error " + httpURLConnection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return jsonResonse;
    }

    private static String decodeInputStreamKey(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        Log.i(TAG, builder.toString());

        return builder.toString();
    }
}


