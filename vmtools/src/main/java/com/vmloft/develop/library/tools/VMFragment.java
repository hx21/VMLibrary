package com.vmloft.develop.library.tools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vmloft.develop.library.tools.utils.VMLog;

/**
 * Created by lzan13 on 2016/7/6.
 * Fragment的基类，进行简单的封装，ViewPager 结合 Fragment 实现数据懒加载
 */
public abstract class VMFragment extends Fragment {

    protected String className = this.getClass().getSimpleName();

    protected VMActivity activity;

    // 是否第一次加载
    protected boolean isFirstLoad = true;
    // 是否已经初始化 View
    protected boolean isInitView = false;
    // 是否显示
    protected boolean isVisible = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        VMLog.d("onAttach: %s", className);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VMLog.d("onCreate: %s", className);
    }

    /**
     * 判断当前 Fragment 是否显示做数据加载判断，此方法只能用在和 ViewPager 一起使用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 当前 Fragment 显示时加载数据
        if (isVisibleToUser) {
            isVisible = true;
            loadData();
        } else {
            isVisible = false;
        }
        VMLog.d("setUserVisibleHint: %s, %b", className, isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayoutId(), container, false);
        VMLog.d("onCreateView: %s", className);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (VMActivity) getActivity();

        initView();
        isInitView = true;
        loadData();
        VMLog.d("onActivityCreated: %s", className);
    }

    //@Override
    //public void onStart() {
    //    super.onStart();
    //    VMLog.d("onStart: %s", className);
    //}
    //
    //@Override
    //public void onResume() {
    //    super.onResume();
    //    VMLog.d("onResume: %s", className);
    //}
    //
    //@Override
    //public void onPause() {
    //    super.onPause();
    //    VMLog.d("onPause: %s", className);
    //}
    //
    //@Override
    //public void onStop() {
    //    super.onStop();
    //    VMLog.d("onStop: %s", className);
    //}
    //
    //@Override
    //public void onDestroyView() {
    //    super.onDestroyView();
    //    VMLog.d("onDestroyView: %s", className);
    //}
    //
    //@Override
    //public void onDetach() {
    //    super.onDetach();
    //    VMLog.d("onDetach: %s", className);
    //}
    //
    //@Override
    //public void onDestroy() {
    //    super.onDestroy();
    //    VMLog.d("onDestroy: %s", className);
    //}

    /**
     * 加载数据方法，是否真正加载由内部判断
     */
    private void loadData() {
        // 只是打印输出当前状态
        if (isFirstLoad) {
            VMLog.d("第一次加载数据 isVisible: %b, %s", isVisible, className);
        } else {
            VMLog.d("不是第一次加载数据 isVisible: %b, %s", isVisible, className);
        }
        // 这里确定要不要执行数据加载
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        // 满足条件，调用数据加载
        initData();
        isFirstLoad = false;
    }

    /**
     * 初始化 Fragment 界面 layout_id
     *
     * @return 返回布局 id
     */
    protected abstract int initLayoutId();

    /**
     * 初始化界面控件，将 Fragment 变量和 View 建立起映射关系
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initData();

    public interface FragmentListener {
        void onAction(int action, Object obj);
    }
}
