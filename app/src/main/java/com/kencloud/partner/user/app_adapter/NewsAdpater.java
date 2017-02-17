package com.kencloud.partner.user.app_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.NewsModel;

import java.util.List;

/**
 * Created by suchismita on 1/30/2017.
 */

public class NewsAdpater extends RecyclerView.Adapter<NewsAdpater.MyViewHolder> {
        private List<NewsModel> newsModel;
        private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView headline;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            headline = (TextView) view.findViewById(R.id.row_item_head);
//            count = (TextView) view.findViewById(R.id.count);
            image = (ImageView) view.findViewById(R.id.row_item_image);

        }
    }

    public NewsAdpater(List<NewsModel> newsModel, final Context context
                           ) {

            this.context = context;
            this.newsModel = newsModel;

        }
        //        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }



    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            NewsModel album = newsModel.get(position);
            holder.headline.setText(album.getTitle());
//        holder.count.setText(album.getNumOfSongs() + " songs");

            // loading album cover using Glide library
            Glide.with(context).load(album.getImage()).into(holder.image);

//            holder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent =new Intent(context,EventzDetailActivity.class);
//                    intent.putExtra("comments", latestFeedModell.get(position).getBulletinTitle());
//                    intent.putExtra("title",latestFeedModell.get(position).getBulletinComments());
//                    intent.putExtra("date",latestFeedModell.get(position).getStartDate());
//                    intent.putExtra("images",latestFeedModell.get(position).getBulletinImage());
//                    context.startActivity(intent);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return newsModel.size();
        }

    }

