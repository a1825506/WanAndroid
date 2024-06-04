package com.tgh.wanandroid.ui;

import android.os.Bundle;

import com.tgh.bymvvm.base.BaseActivity;
import com.tgh.wanandroid.R;

import com.tgh.wanandroid.databinding.ActivityMainBinding;
import com.tgh.wanandroid.viewmodel.MainViewModel;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    public static boolean isLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        showContentView();
        isLaunch = true;
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}