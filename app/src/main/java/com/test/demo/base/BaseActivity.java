package com.test.demo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-25
 * 描述：
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    public Context context;
    public Activity mActivity;
    public T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> tClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Method inflate = tClass.getMethod("inflate", LayoutInflater.class);
                binding = (T) inflate.invoke(null, getLayoutInflater());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(binding.getRoot());
        }

        initData();
        onclickListener();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 点击事件
     */
    protected abstract void onclickListener();


    protected void launchActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(mActivity, activity));
    }

    protected void launchActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(mActivity, activity), requestCode);
    }

    protected void launchActivity(Class<? extends Activity> activity, Activity finishCurrentActivity) {
        startActivity(new Intent(mActivity, activity));
        finishCurrentActivity.finish();
    }

    public void onBackKeyPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        onBackKeyPressed();
    }
}