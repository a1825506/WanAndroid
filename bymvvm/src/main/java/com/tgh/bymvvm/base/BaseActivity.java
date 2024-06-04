package com.tgh.bymvvm.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.tgh.bymvvm.R;
import com.tgh.bymvvm.databinding.ActivityBaseBinding;
import com.tgh.bymvvm.utils.ClassUtil;
import com.tgh.bymvvm.utils.CommonUtils;
import com.tgh.bymvvm.utils.StatusBarUtil;

public abstract class BaseActivity  <VM extends AndroidViewModel,SV extends ViewDataBinding> extends AppCompatActivity {

    private ActivityBaseBinding mBaseBinding;
    //布局view
    protected SV bindingView;
    //ViewModel
    protected VM viewModel;
    //通用error提示
    private View errorView;
    //通用loading
    private View loadingView;
    private AnimationDrawable mAnimationDrawable;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutResID){

        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base,null,false);
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this),layoutResID,null,false);

        //添加content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        loadingView = ((ViewStub) findViewById(R.id.vs_loading)).inflate();
        ImageView img = loadingView.findViewById(R.id.img_progress);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        setTitleBar(mBaseBinding.toolBar);
        bindingView.getRoot().setVisibility(View.GONE);
        initStatusBar();
        initViewModel();
    }


    protected void initStatusBar() {
        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, CommonUtils.getColor(this, R.color.colorToolBar), 0);
    }

    /**
     * 设置titlebar
     */
    private void setTitleBar(Toolbar toolBar) {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });

    }

    public void setTitle(CharSequence text){
        mBaseBinding.toolBar.setTitle(text);
    }

    public void setNoTitle() {
        mBaseBinding.toolBar.setVisibility(View.GONE);
    }

    public void showLoading(){
        if(loadingView != null && loadingView.getVisibility() != View.VISIBLE){
            loadingView.setVisibility(View.VISIBLE);
        }
        //开始动画
        if(!mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if(loadingView !=null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError(){
        if(loadingView != null && loadingView.getVisibility() != View.GONE){
            loadingView.setVisibility(View.GONE);
        }
        //停止动画
        if(mAnimationDrawable.isRunning()){
            mAnimationDrawable.stop();
        }
        if(errorView == null){
            ViewStub viewStub =(ViewStub) findViewById(R.id.vs_error_refresh);
            errorView = viewStub.inflate();
            //点击加载失败布局
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    onRefresh();
                }
            });
        }else{
            errorView.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    protected Activity getActivity(){return this;}

    /**
     * 失败后点击刷新
     */
    protected void onRefresh(){}


    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
    }


    /**
     *处理横屏
     * */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) {
            getResources();
        }
    }

    /**
     * 禁止改变字体大小
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
