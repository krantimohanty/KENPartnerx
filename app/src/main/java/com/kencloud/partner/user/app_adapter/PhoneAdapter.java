package com.kencloud.partner.user.app_adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.Phone;

import java.util.List;

/**
 * Created by suchismita.p on 11/24/2016.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {

    private Context mContext;
    private List<Phone> phoneList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView phoneType, phoneNo;
//        public ImageView dots;

        public MyViewHolder(View view) {
            super(view);
            phoneType = (TextView) view.findViewById(R.id.phoneType);
            phoneNo = (TextView) view.findViewById(R.id.phoneNo);
//            dots = (ImageView) view.findViewById(R.id.dots);
        }
    }


    public PhoneAdapter(Context mContext, List<Phone> phoneList) {
        this.mContext = mContext;
        this.phoneList = phoneList;
    }

    @Override
    public PhoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phone_list_item, parent, false);

        return new PhoneAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhoneAdapter.MyViewHolder holder, int position) {
        Phone album = phoneList.get(position);
        holder.phoneType.setText(album.getPhoneType());
        holder.phoneNo.setText(album.getPhoneNo());
//        holder.dots.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.dots);
//            }
//        });
    }

    /**
     * /**
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
        return phoneList.size();
    }
}



