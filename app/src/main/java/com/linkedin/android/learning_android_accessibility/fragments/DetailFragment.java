package com.linkedin.android.learning_android_accessibility.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.linkedin.android.learning_android_accessibility.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    // setup interface for the container activity
    public interface AnimationListener {
        void onAnimationEnded();
    }
    AnimationListener mAnimationCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mAnimationCallback = (AnimationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement the animation listener");
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // NO-OP
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimationCallback.onAnimationEnded();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // NO-OP
            }
        });

        return anim;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
