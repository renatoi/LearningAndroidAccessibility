package com.linkedin.android.learning_android_accessibility.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkedin.android.learning_android_accessibility.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabLayoutFragment extends Fragment {

    private static final String TEXT_KEY = "text_key";
    private String text;

    public TabLayoutFragment() {
        // Required empty public constructor
    }

    public static TabLayoutFragment newInstance(String text) {
        Bundle args = new Bundle();
        TabLayoutFragment fragment = new TabLayoutFragment();
        args.putString(TEXT_KEY, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            text = getArguments().getString(TEXT_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        TextView textView = v.findViewById(R.id.tab_layout_textview);
        textView.setText(Html.fromHtml(text));
        textView.setMovementMethod(new ScrollingMovementMethod());

        return v;
    }
}
