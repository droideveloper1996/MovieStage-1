package multilangyuage.project.com.moviestage_1;

/**
 * Created by Abhishek on 05/05/2017.
 */

public class Movie {


    private String mMovieTitle;
    private String mAdult;
    private String mOverview;
    private String mrelease;
    private String mOriginalTitle;
    private String mLanguage;
    private String mTitle;
    private String mVote;
    private String mPopularity;

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getmAdult() {
        return mAdult;
    }

    public void setmAdult(String mAdult) {
        this.mAdult = mAdult;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getMrelease() {
        return mrelease;
    }

    public void setMrelease(String mrelease) {
        this.mrelease = mrelease;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmVote() {
        return mVote;
    }

    public Movie(String mMovieTitle,
                 String mAdult,
                 String mOverview,
                 String mrelease,
                 String mOriginalTitle,
                 String mLanguage,
                 String mTitle,
                 String mVote,
                 String mPopularity)
    {
        this.mMovieTitle = mMovieTitle;
        this.mAdult = mAdult;
        this.mOverview = mOverview;
        this.mrelease = mrelease;
        this.mOriginalTitle = mOriginalTitle;
        this.mLanguage = mLanguage;
        this.mTitle = mTitle;
        this.mVote = mVote;
        this.mPopularity = mPopularity;
    }

    public void setmVote(String mVote) {
        this.mVote = mVote;

    }

    public String getmPopularity() {
        return mPopularity;
    }

    public void setmPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }
}
