package com.tgh.wanandroid.ui;

import android.os.Bundle;

import com.tgh.bymvvm.base.BaseFragment;
import com.tgh.bymvvm.base.NoViewModel;
import com.tgh.wanandroid.R;
import com.tgh.wanandroid.databinding.FragmentContentBinding;
import com.tgh.wanandroid.ui.wan.HomeFragment;
import com.tgh.wanandroid.ui.wan.SquareFragment;
import com.tgh.wanandroid.ui.wan.WendaFragment;
import com.tgh.wanandroid.view.CommonTabPagerAdapter;

import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class WanFragment extends BaseFragment<NoViewModel, FragmentContentBinding> implements CommonTabPagerAdapter.TabPagerListener {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        CommonTabPagerAdapter myAdapter = new CommonTabPagerAdapter(getChildFragmentManager(), Arrays.asList("玩安卓", "广场", "问答"));
        myAdapter.setListener(this);
        bindingView.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGank.setOffscreenPageLimit(2);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();
    }
    @Override
    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return SquareFragment.newInstance();
            case 2:
                return WendaFragment.newInstance();
        }
        return HomeFragment.newInstance();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_content;
    }
}
