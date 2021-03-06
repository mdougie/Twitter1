package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by madelynd on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    //pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    //for each row, put the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values based on the position of each element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get data according to position
        Tweet tweet = mTweets.get(position);

        //populate views according to data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        //holder.tvUid.setText(Long.toString(tweet.user.uid));
        //String displayName;
        //displayName = new String();
        //displayName = "@" + tweet.user.screenName;
        holder.tvName.setText(tweet.user.screenName);
        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));


        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used TODO Error potential
    public void addAll(List<Tweet> list) {
        for(int i=0; i<list.size(); i++){
            list.add(list.get(i));
            notifyDataSetChanged();
        }
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String newDate = "";
        newDate = relativeDate.substring(0, relativeDate.length()-10);
//        if(relativeDate.substring(relativeDate.length()-12, relativeDate.length()-11).equals("m")){
//            newDate = relativeDate.substring(0,)
//        }
//        if(relativeDate.substring(relativeDate.length()-12, relativeDate.length()-11).equals("s")){
//
//        }

        return newDate;
    }

    public String reformatRelativeDate(String relativeDate, String rawDate){
        String[] parts = relativeDate.split(" ");
        String[] date = relativeDate.split(" ");
        if (parts[0].equals("in")){
            return relativeDate;
        }else if(parts[1].equals("month") || parts[1].equals("months")){
            return date[1];
        }else if (parts[1].equals("year") || parts[1].equals("years")){
            return date[5];
        }else{
            return parts[0]+parts[1].charAt(0);
        }
    }

        //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        //public TextView tvUid;
        public TextView tvName;
        public TextView tvTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            //tvUid = (TextView) itemView.findViewById(R.id.tvUid);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.i("CHECKING", "ONCLICK");
            //get item position
            int position = getAdapterPosition();
            //does position exist in view
            if (position != RecyclerView.NO_POSITION) {
                //get tweet at position
                Log.i("CHECKING", "SUCCESS");
                Tweet tweet = mTweets.get(position);
                //create intent for new activity
                Intent intent = new Intent(context, TweetDetail.class);
                //serialize movie with parceler
                intent.putExtra("tweet", Parcels.wrap(tweet));
                //show activity
                context.startActivity(intent);
            }
        }
    }


}
