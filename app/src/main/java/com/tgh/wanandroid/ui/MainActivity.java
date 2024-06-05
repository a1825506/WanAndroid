package com.tgh.wanandroid.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tgh.bymvvm.base.BaseActivity;
import com.tgh.wanandroid.R;

import com.tgh.wanandroid.databinding.ActivityMainBinding;
import com.tgh.wanandroid.view.CommonTabPagerAdapter;
import com.tgh.wanandroid.view.OnMyPageChangeListener;
import com.tgh.wanandroid.viewmodel.MainViewModel;

import java.util.Arrays;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements View.OnClickListener,CommonTabPagerAdapter.TabPagerListener {

    public static boolean isLaunch;
    public boolean isClickCloseApp;
    private ViewPager vpContent;
    private ImageView ivTitleTwo;
    private ImageView ivTitleOne;
    private ImageView ivTitleThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        showContentView();
        isLaunch = true;
        initView();
        initContentFragment();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void initView() {
        setNoTitle();
        setSupportActionBar(bindingView.include.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        vpContent = bindingView.include.vpContent;
        ivTitleOne = bindingView.include.ivTitleOne;
        ivTitleTwo = bindingView.include.ivTitleTwo;
        ivTitleThree = bindingView.include.ivTitleThree;
        bindingView.include.llTitleMenu.setOnClickListener(this);
        bindingView.include.ivTitleOne.setOnClickListener(this);
        bindingView.include.ivTitleTwo.setOnClickListener(this);
        bindingView.include.ivTitleThree.setOnClickListener(this);
    }

    private void initContentFragment() {
        // 注意使用的是：getSupportFragmentManager
        CommonTabPagerAdapter adapter = new CommonTabPagerAdapter(getSupportFragmentManager(), Arrays.asList("", "", ""));
        adapter.setListener(this);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(new OnMyPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setCurrentItem(0);
                        break;
                    case 1:
                        setCurrentItem(1);
                        break;
                    case 2:
                        setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });
        setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.iv_title_one){
            if (vpContent.getCurrentItem() != 0) {
                setCurrentItem(0);
            }
        }else if(viewId == R.id.iv_title_two){
            if (vpContent.getCurrentItem() != 1) {
                setCurrentItem(1);
            }
        }else if(viewId == R.id.iv_title_three){
            if (vpContent.getCurrentItem() != 2) {
                setCurrentItem(2);
            }
        }else if(viewId == R.id.ll_title_menu){
            // 开启菜单
            bindingView.drawerLayout.openDrawer(GravityCompat.START);
        }
//        else if(viewId == R.id.ll_nav_exit){
//            // 退出应用
//            isClickCloseApp = true;
//            finish();
//        }
    }

    /**
     * 切换页面
     *
     * @param position 分类角标
     */
    private void setCurrentItem(int position) {
        boolean isOne = false;
        boolean isTwo = false;
        boolean isThree = false;
        switch (position) {
            case 0:
                isOne = true;
                break;
            case 1:
                isTwo = true;
                break;
            case 2:
                isThree = true;
                break;
            default:
                isTwo = true;
                break;
        }
        vpContent.setCurrentItem(position);
        ivTitleOne.setSelected(isOne);
        ivTitleTwo.setSelected(isTwo);
        ivTitleThree.setSelected(isThree);
    }

    @Override
    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new WanFragment();
            case 1:
                return new WanCenterFragment();
            case 2:
                return new WanProjectFragment();
        }
        return new WanFragment();
    }
}