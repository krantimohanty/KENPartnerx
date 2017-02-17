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
import com.kencloud.partner.user.app_model.Address_Info_Comp;

import java.util.List;

/**
 * Created by suchismita.p on 11/4/2016.
 */

public class Form1Adapter extends RecyclerView.Adapter<Form1Adapter.MyViewHolder> {
    RecyclerView mRecyclerView;
    private Context mContext;
    private List<Address_Info_Comp> addressList;
    long user_id;
   private OnLoadMoreListener onLoadMoreListener;
private  boolean isLoading;
    private  int id;
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



    public Form1Adapter(Context mContext, List<Address_Info_Comp> addressList, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.addressList = addressList;
        this.mRecyclerView=mRecyclerView;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_detail, parent, false);

        return new MyViewHolder(itemView);
    }


//    public View getView(final int position, View view, ViewGroup parent) {
//
//        ViewHolder holder = null;
//
//        final int index = position;
//
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.address_detail, parent, false);
//
//			/*LayoutInflater inflater = (LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			// (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = inflater.inflate(R.layout.chatrow, null);*/
//            holder = new ViewHolder();
//
//            holder. headline= (TextView) view.findViewById(R.id.headline);
//            holder. address1= (TextView) view.findViewById(R.id.address1);
//            holder. address2= (TextView) view.findViewById(R.id.address2);
//            holder. city= (TextView) view.findViewById(R.id.city);
//            holder. state= (TextView) view.findViewById(R.id.state);
//            holder. country= (TextView) view.findViewById(R.id.country);
//            holder. pin= (TextView) view.findViewById(R.id.pin);
//
//
//            holder.delete = (ImageView) view
//                    .findViewById(R.id.delete);
//
//
//            view.setTag(holder);
//        } else {
//
//            holder = (ViewHolder) view.getTag();
//        }
//Address_Info_Comp form;
//        form=addressList.get(position);
//        holder.headline.setText(form.getAddressType());
//        holder.address1.setText(form.getAddress1());
//        holder.address2.setText(form.getAddress2());
//        holder.city.setText(form.getCity());
//        holder.state.setText(form.getState() + "-");
//        holder.country.setText(form.getCountry());
//        holder.pin.setText(form.getPin());
////        holder.delete.setTag(form.getId());
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
//                adb.setTitle("Delete?");
//                adb.setMessage("Are you sure you want to delete ");
//                final int user_id = Integer.parseInt(view.getTag().toString());
//                adb.setNegativeButton("Cancel", null);
//                adb.setPositiveButton("Ok",
//                        new AlertDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//
//
//                                // MyDataObject.remove(positionToRemove);
//                                DatabaseHandler dBHandler = new DatabaseHandler(mContext);
////                                dBHandler.deleteAddress(user_id);
////Form1Adapter adapter=new Form1Adapter(mContext,addressList);
////                                adapter.notifyDataSetChanged();
//
//                                dBHandler.deleteAddress((addressList.get((int) view.getTag()).getId()));
//                                addressList.remove(position);
//                                notifyDataSetChanged();
//                            }
//                        });
//                adb.show();
//            }
//
//        });
//
//        return view;
//    }

//    public class MyViewHolder {
//        TextView headline;
//        TextView address1;
//        TextView address2;
//        TextView city;
//        TextView state;
//        TextView country;
//        TextView pin;
//        ImageView delete;
//    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        Address_Info_Comp album = addressList.get(position);
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        holder.addressType.setText(album.getApl_AddressType());
        holder.address1.setText(album.getApl_Address_Street1());
        holder.address2.setText(album.getApl_Address_Street2());
        holder.city.setText(album.getApl_Address_City());
        holder.state.setText(album.getApl_Address_State() + " - ");
        holder.country.setText(album.getApl_Address_Country());
        holder.pin.setText(album.getApl_Address_ZIP());

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
        addressList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressList.size());

    }
//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.recycleview_bounce);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setLoaded() {
        isLoading = true;
    }
    @Override
    public int getItemCount() {
        return addressList.size();
    }
}


