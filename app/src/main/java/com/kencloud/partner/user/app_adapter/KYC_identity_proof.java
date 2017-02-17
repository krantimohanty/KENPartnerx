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
import com.kencloud.partner.user.app_model.KYC_id;

import java.util.List;

/**
 * Created by suchismita.p on 11/30/2016.
 */

public class KYC_identity_proof extends RecyclerView.Adapter<KYC_identity_proof.MyViewHolder> {
    private String selectedFilePath;
    private Context mContext;
    private List<KYC_id> kyc_id;
    private static final int PICK_FILE_REQUEST = 1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idType, refId;
        public Button upload;

        public MyViewHolder(View view) {
            super(view);
            idType = (TextView) view.findViewById(R.id.idtype);
            refId = (TextView) view.findViewById(R.id.reference_no_id);
            upload=(Button)view.findViewById(R.id.upload_id);
//            dots = (ImageView) view.findViewById(R.id.dots);
        }
    }

    public KYC_identity_proof(Context mContext, List<KYC_id> kyc_id) {
        this.mContext = mContext;
        this.kyc_id = kyc_id;
    }

    @Override
    public KYC_identity_proof.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kyc_details_id, parent, false);

        return new KYC_identity_proof.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KYC_identity_proof.MyViewHolder holder, int position) {
        KYC_id album = kyc_id.get(position);
        holder.idType.setText(album.getIdentity_type());
        holder.refId.setText(album.getRef_id());
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
        popup.setOnMenuItemClickListener(new KYC_identity_proof.MyMenuItemClickListener());
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
        return kyc_id.size();
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