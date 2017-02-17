package com.kencloud.partner.user.app_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.HApp;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Created by suchismita.p on 11/3/2016.
 */

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.MyViewHolder> {

    private List<HApp> hmodel;
    Context mContext;
    private Bitmap bitmap = null;
    RecyclerView mRecyclerView;

    public PartnerAdapter() {

    }
    public PartnerAdapter(Context mContext, List<HApp> model) {
        this.mContext = mContext;
        this.hmodel = model;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView pic;
        public MyViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.row_head);
//            count = (TextView) view.findViewById(R.id.count);
            pic = (ImageView) view.findViewById(R.id.picture);
//            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partner_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HApp album = hmodel.get(position);
        holder.text.setText(album.getText());
//        holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImage()).into(holder.pic);
//
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return hmodel.size();
    }
}



