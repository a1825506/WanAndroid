package com.tgh.wanandroid.ui.wan;

import android.content.Context;

import com.tgh.bymvvm.base.BaseFragment;
import com.tgh.wanandroid.databinding.FragmentWanAndroidBinding;
import com.tgh.wanandroid.viewmodel.wan.WanAndroidListViewModel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class HomeFragment extends BaseFragment<WanAndroidListViewModel, FragmentWanAndroidBinding> {
    private FragmentActivity activity;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }
}
