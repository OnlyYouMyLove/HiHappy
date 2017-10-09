package com.best.hihappy.mvp.view.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.best.hihappy.R;


/**
 * Created by FuKaiqiang on 2017-08-29.
 */

public class MyCenterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mycenterfragment_layout,container,false);
        return view;
    }
}
