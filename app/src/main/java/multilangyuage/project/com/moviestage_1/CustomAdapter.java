package multilangyuage.project.com.moviestage_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static multilangyuage.project.com.moviestage_1.NetworkUtils.TAG;

/**
 * Created by Abhishek on 05/05/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private int mNumberofViews;
    List<URL> urls = new ArrayList<>();
    Context context;

    public CustomAdapter(Context context, List<URL> url) {
        this.mNumberofViews = url.size();
        this.context = context;
        urls = url;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        URL url = urls.get(position);
        Log.i(TAG, "onBindView " + url.toString());

        Picasso.with(context).load(url.toString()).into(holder.imageView);
        //holder.imageView.setImageResource(R.drawable.movie512);
    }

    @Override
    public int getItemCount() {
        return mNumberofViews;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.listItem_ImageView);
        }

    }

}
