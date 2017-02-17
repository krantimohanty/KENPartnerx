package com.kencloud.partner.user.app_adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.joanzapata.iconify.widget.IconButton;
import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.NotificationDataModel;
import com.kencloud.partner.user.app_util.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kranti on 22/3/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter {

    private List<NotificationDataModel> notiFeedModel = new ArrayList<>();
    private Context context;
    RecyclerView mRecyclerView;
    private AppCompatTextView ui_notification_num = null;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount, visibleItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public NotificationAdapter(List<NotificationDataModel> notiFeedModel, Context context, RecyclerView mRecyclerView) {
        this.notiFeedModel = notiFeedModel;
        this.context = context;
        this.mRecyclerView = mRecyclerView;

        try {
            Log.d("dataFrag", this.notiFeedModel.size() + "");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_list, parent, false);
            vh = new DataObjectHolder(view);
            // DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);

            if (notiFeedModel.size() == 0) {
                v.setVisibility(View.GONE);
            } else {
                v.setVisibility(View.VISIBLE);
            }
        }

        return vh;
    }

   /* @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);


        DataObjectHolder dataObjectHolder = new DataObjectHolder(parent.getContext(), view);
        return dataObjectHolder;
    }*/


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataObjectHolder) {
            try {
                ((DataObjectHolder) holder).row_item_title.setText(Html.fromHtml(notiFeedModel.get(position).getDescription()));
                ((DataObjectHolder) holder).row_item_date.setText(Html.fromHtml(notiFeedModel.get(position).getDate()));

                if (notiFeedModel.get(position).getLink() == null) {
                    ((DataObjectHolder) holder).row_item_details.setVisibility(View.GONE);
                } else {
                    ((DataObjectHolder) holder).row_item_details.setVisibility(View.VISIBLE);
                    ((DataObjectHolder) holder).row_item_details.setOnClickListener(sendPostId(notiFeedModel.get(position).getLink()));
                }

            } catch (Exception e) {
            }
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        private Context context;
        private AppCompatTextView row_item_title;
        private AppCompatTextView row_item_date;
        public AppCompatTextView ui_notification_num;
        private IconButton row_item_details;
        private CardView cardView;


        public DataObjectHolder(View itemView) {
            super(itemView);
            row_item_title = (AppCompatTextView) itemView.findViewById(R.id.notifivation_list_text);
            row_item_date = (AppCompatTextView) itemView.findViewById(R.id.notification_date);
            row_item_details = (IconButton) itemView.findViewById(R.id.details);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            Log.i("LOG_TAG", "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {

            //*********************************(IMPORTANT)
            //get Flag n get flag to get the respective activity content

           /* Intent intent = new Intent(context, EventzDetailActivity.class);
            context.startActivity(intent);*/

        }
    }


    private View.OnClickListener sendPostId(final String url) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return notiFeedModel.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    @Override
    public int getItemCount() {
        return this.notiFeedModel.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ContentLoadingProgressBar) v.findViewById(R.id.progress1);
        }
    }
}