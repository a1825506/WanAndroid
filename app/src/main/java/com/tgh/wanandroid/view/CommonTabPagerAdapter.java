package com.tgh.wanandroid.view;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class CommonTabPagerAdapter extends FragmentPagerAdapter {
    private int pageCount=0;
    private List<String> mList = null;
    private TabPagerListener mTabPageListener;

    public CommonTabPagerAdapter(@NonNull FragmentManager fm, List<String> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        initData(list);
    }

    public void initData(List<String> list){
        if(list ==null || list.isEmpty()){
            throw new ExceptionInInitializerError("list can't be null or empty");
        }
        mList = list;
        this.pageCount = list.size();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(mTabPageListener != null){
            return mTabPageListener.getFragment(position);
        }else
            return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    public void setListener(TabPagerListener tabPagerListener){
        this.mTabPageListener = tabPagerListener;
    }

     public interface TabPagerListener{
          Fragment getFragment(int position);
    }
}
