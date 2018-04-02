package com.linkedin.android.learning_android_accessibility.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkedin.android.learning_android_accessibility.R;
import com.linkedin.android.learning_android_accessibility.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListAdapter.ItemClickListener {

    public ListFragment() {
        // Required empty public constructor
    }
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    // setup interface for the container activity
    ItemClickListener mCallback;
    public interface ItemClickListener {
        public void onListItemClicked(View view, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        // populate some data
        List<String> listItems = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            listItems.add("Option " + i);
        }

        // set up adapter
        ListAdapter adapter = new ListAdapter(listItems);
        adapter.setClickListener(this);

        // set up recyclerview
        RecyclerView recyclerView = v.findViewById(R.id.single_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return v;
    }

    @Override
    public void onItemClicked(View view, int position) {
        mCallback.onListItemClicked(view, position);
    }
}
