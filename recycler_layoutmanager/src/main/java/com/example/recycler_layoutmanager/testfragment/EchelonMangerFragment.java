package com.example.recycler_layoutmanager.testfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycler_layoutmanager.R;

public class EchelonMangerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","EchelonMangerFragment");
        return inflater.inflate(R.layout.fragment_echelon,container,false);
    }
}
