package com.kencloud.partner.user.app_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kencloud.partner.user.OrderDetailActivity;
import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.NewOrderModel;

import java.util.List;

/**
 * Created by suchismita.p on 11/23/2016.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewOrderModel> orderList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, orderId, status, amount, paymentMode;
        public ImageView dots;
public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            orderId = (TextView) view.findViewById(R.id.orderNo);
            status = (TextView) view.findViewById(R.id.status);
            amount = (TextView) view.findViewById(R.id.amount);
            paymentMode = (TextView) view.findViewById(R.id.payment_mode);
            dots = (ImageView) view.findViewById(R.id.dots);
            cardView=(CardView)view.findViewById(R.id.card_view);
        }
    }

    public NewOrderAdapter() {

    }

    public NewOrderAdapter(Context mContext, List<NewOrderModel> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NewOrderAdapter.MyViewHolder holder, final int position) {
        NewOrderModel album = orderList.get(position);
        holder.name.setText(album.getCustomerName());
        holder.status.setText(album.getStatus() );
        holder.orderId.setText(album.getOrderNo());
        holder.amount.setText(album.getAmount());
        holder.paymentMode.setText(album.getPaymentMode());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
//                intent.putExtra("person_name", NewOrderModel.get(position).());
//                intent.putExtra("enquiry_for", NewOrderModel.get(position).getEnquiryType());
//                intent.putExtra("enquiry_query", NewOrderModel.get(position).getQueries());
//                intent.putExtra("enquiry_phno", NewOrderModel.get(position).getPhoneNo());
//                intent.putExtra("enquiry_area", enquiryModels.get(position).getArea());
                mContext.startActivity(intent);
            }
        });
//        holder.country.setText(album.getCountry());
//        holder.pin.setText(album.getPin());
//        holder.phoneno.setText(album.getPhoneno());
        holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.dots);
            }
        });
    }

    /**
     /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.selection_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit:
                    Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}




