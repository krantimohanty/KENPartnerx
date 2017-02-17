package com.kencloud.partner.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kencloud.partner.user.app_adapter.NewsAdpater;
import com.kencloud.partner.user.app_model.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private    RecyclerView newsRecycler;
    private   List<NewsModel> newsList;
    private   NewsAdpater newsAdpater;
//    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        newsRecycler =(RecyclerView) rootView.findViewById(R.id.recyclerview);
        newsRecycler.setHasFixedSize(true);
        newsList = new ArrayList<>();

//        LinearLayoutManager newsLayout = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        newsRecycler.setLayoutManager(mLayoutManager);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mLayoutManager.scrollToPosition(0);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        newsRecycler.setItemAnimator(new DefaultItemAnimator());
        newsAdpater = new NewsAdpater(newsList, getContext());
        newsRecycler.setAdapter(newsAdpater);
        prepareAlbums();
        return rootView;
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.press_release1,
                R.drawable.press_release2,
                R.drawable.press_release3,
//                R.drawable.product4,
                R.drawable.banner04,

        };
  int[] headline = new int[]
          {
          R.string.press_release1,
                  R.string.press_release2,
          };

        NewsModel a = new NewsModel("Brand Promotion", covers[0], headline[0]);
        newsList.add(a);

        a = new NewsModel("Brand Promotion",  covers[1], headline[1]);
        newsList.add(a);

        a = new NewsModel("Brand Promotion",  covers[2], headline[1]);
        newsList.add(a);
    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
