package com.kencloud.partner.user.app_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_model.Address_Info_Individual;

import java.util.List;

/**
 * Created by suchismita.p on 11/5/2016.
 */

public class Form2Adapter extends RecyclerView.Adapter<Form2Adapter.MyViewHolder> {
    private Context mContext;
    private List<Address_Info_Individual> form2List;
    RecyclerView mRecyclerView;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id,addressType, address1, address2, city, state, country, pin, phoneno;
        public ImageView edit,delete;
        public CardView cardview;
        public MyViewHolder(View view) {
            super(view);
            addressType = (TextView) view.findViewById(R.id.headline);
            address1 = (TextView) view.findViewById(R.id.address1);
            address2 = (TextView) view.findViewById(R.id.address2);
            city = (TextView) view.findViewById(R.id.city);
            state = (TextView) view.findViewById(R.id.state);
            country = (TextView) view.findViewById(R.id.country);
            pin = (TextView) view.findViewById(R.id.pin);
//            phoneno = (TextView) view.findViewById(R.id.phno);
            delete= (ImageView)view.findViewById(R.id.delete);
            cardview=(CardView)view.findViewById(R.id.card_view);


        }
    }

    public Form2Adapter(Context mContext, List<Address_Info_Individual> form2List, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.form2List = form2List;
        this.mRecyclerView=mRecyclerView;
    }

    @Override
    public Form2Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_detail, parent, false);

        return new Form2Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Form2Adapter.MyViewHolder viewHolder, final int position) {
        Address_Info_Individual album = form2List.get(position);
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        holder.addressType.setText(album.getAddressType());
        holder.address1.setText(album.getAddress1());
        holder.address2.setText(album.getAddress2());
        holder.city.setText(album.getCity());
        holder.state.setText(album.getState() + " - ");
        holder.country.setText(album.getCountry());
        holder.pin.setText(album.getPin());
//animate(holder);
//        holder.phoneno.setText(album.getPhoneno());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete ");
//                final int user_id = Integer.parseInt(view.getTag().toString());
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                deleteItem(position);

                                holder.cardview.setVisibility(View.GONE);
                                // MyDataObject.remove(positionToRemove);
//                                DatabaseHandler dBHandler = new DatabaseHandler(mContext);
//                                dBHandler.deleteAddress(user_id);

//                                adapter.notifyDataSetChanged();

//                                dBHandler.deleteAddress(f.getId());
//                                addressList.remove(position);
//                                Form1Adapter adapter=new Form1Adapter(mContext,addressList);

                            }
                        });
                adb.show();
            }


        });
    }



    private void deleteItem(int position) {
        form2List.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, form2List.size());

    }
//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.recycleview_bounce);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }



@Override
        public int getItemCount () {
            return form2List.size();
        }
    }


