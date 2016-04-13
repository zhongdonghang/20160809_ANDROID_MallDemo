package com.luoyp.brnmall.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luoyp.brnmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends Fragment {


    public ShopCarFragment() {
        // Required empty public constructor
    }

    public static ShopCarFragment newInstance(String param1) {
        ShopCarFragment fragment = new ShopCarFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);
//        Bundle bundle = getArguments();
//        String agrs1 = bundle.getString("agrs1");
//        TextView tv = (TextView)view.findViewById(R.id.tv_location);
//        tv.setText(agrs1);
        return view;
    }
}
