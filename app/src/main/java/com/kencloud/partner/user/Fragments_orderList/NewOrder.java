package com.kencloud.partner.user.Fragments_orderList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_adapter.NewOrderAdapter;
import com.kencloud.partner.user.app_model.NewOrderModel;

import java.util.ArrayList;
import java.util.List;

public class NewOrder extends Fragment {
    private RecyclerView recyclerView;
    private View rootView;
    LinearLayoutManager mLinearLayoutManager;
List<NewOrderModel> orderlist;
   NewOrderAdapter orderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_new_order, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.new_order_list);
        recyclerView.setHasFixedSize(true);
        orderlist = new ArrayList<>();
        orderAdapter = new NewOrderAdapter(getContext(), orderlist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CompanyActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.scrollToPosition(0);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(orderAdapter);
        newOrder();
        return rootView;
    }
   private void newOrder(){
NewOrderModel order=new NewOrderModel("SC-SCTL-203","Sagar Das", "new", "2883823", "Online Transfer");
orderlist.add(order);
       order=new NewOrderModel("SC-SCTL-203","Sagar Das", "new", "2883823", "Online Transfer");
       orderlist.add(order);
   }
}
