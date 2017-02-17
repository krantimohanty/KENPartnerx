package com.kencloud.partner.user.app_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.KYC_address;

import java.util.List;

/**
 * Created by suchismita.p on 11/30/2016.
 */

public class KYC_address_proof extends RecyclerView.Adapter<KYC_address_proof.MyViewHolder> {
    private String selectedFilePath;
    private Context mContext;
    private List<KYC_address> kyc_address;
    private static final int PICK_FILE_REQUEST = 1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView addressType, refId;
        public Button upload;

        public MyViewHolder(View view) {
            super(view);
            addressType = (TextView) view.findViewById(R.id.addresstype);
            refId = (TextView) view.findViewById(R.id.reference_no);
            upload=(Button)view.findViewById(R.id.upload);
//            dots = (ImageView) view.findViewById(R.id.dots);
        }
    }

    public KYC_address_proof(Context mContext, List<KYC_address> kyc_address) {
        this.mContext = mContext;
        this.kyc_address = kyc_address;
    }

    @Override
    public KYC_address_proof.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kyc_details_address, parent, false);

        return new KYC_address_proof.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KYC_address_proof.MyViewHolder holder, int position) {
        KYC_address album = kyc_address.get(position);
        holder.addressType.setText(album.getAddresType());
        holder.refId.setText(album.getRef_address());
holder.upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showFileChooser();

    }
});
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
        popup.setOnMenuItemClickListener(new KYC_address_proof.MyMenuItemClickListener());
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
        return kyc_address.size();
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }




}

