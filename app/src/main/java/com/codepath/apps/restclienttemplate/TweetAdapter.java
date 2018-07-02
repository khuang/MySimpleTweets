package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;

    //pass in Tweets array in constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }

    //for each row, inflate the layout and cache references in the ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }
    //bind the views based on position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to position
        Tweet tweet = mTweets.get(position);

        //populate the views based on this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }


    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivProfileImage) public ImageView ivProfileImage;
        @BindView(R.id.tvUserName) public TextView tvUsername;
        @BindView(R.id.tvBody) public TextView tvBody;

        public ViewHolder(View itemView){
            super(itemView);

            //bind views from xml file
            ButterKnife.bind(this, itemView);
        }
    }
}
