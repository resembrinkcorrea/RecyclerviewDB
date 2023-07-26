package com.example.recyclerviewdb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdb.Model.MListAppShow;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class AddBottomSheetDialogFragment extends BottomSheetDialogFragment {

    RecyclerView recycler_apps;
    View rootview;
    static ArrayList<MListAppShow> mListAppShows = new ArrayList();


    public static AddBottomSheetDialogFragment newInstance() {
        return new AddBottomSheetDialogFragment();
    }

    public static void apkArrayList(ArrayList arrayList) {
        mListAppShows = arrayList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.botonsheet_fragment, container,
                false);
        installedApps();

        return rootview;
    }

    public void installedApps() {
        recycler_apps = rootview.findViewById(R.id.recycler_apps);
        RecyclerView.Adapter adapter;
        adapter = new AdapterRecyclerApps(mListAppShows, getContext());
        recycler_apps.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycler_apps.setAdapter(adapter);
    }
}