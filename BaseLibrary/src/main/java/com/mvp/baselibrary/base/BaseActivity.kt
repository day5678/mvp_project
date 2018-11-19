package com.mvp.baselibrary.base

import android.os.Bundle
import com.mvp.baselibrary.manager.APPActivityManager
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.android.AndroidInjection
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils.convertActivityToTranslucent
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

/**
 * Activity基类
 *
 * @auth wxf on 2018/11/14.
 */
abstract class BaseActivity : RxAppCompatActivity(), SwipeBackActivityBase {
    // 滑动关闭帮助类
    private var mSwipeHelper: SwipeBackActivityHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 打印日志
        Logger.d("$localClassName onCreate")

        // 放入堆栈
        APPActivityManager.instance.addActivity(this)

        // 初始化滑动关闭帮助类
        mSwipeHelper = SwipeBackActivityHelper(this)
        mSwipeHelper!!.onActivityCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 打印日志
        Logger.d("$localClassName onDestroy")
        // 移出堆栈
        APPActivityManager.instance.addActivity(this)
    }

    /**
     * onPostCreate方法是指onCreate方法彻底执行完毕的回调,在onStart之后
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // 初始化滑动关闭Activity
        mSwipeHelper!!.onPostCreate()
    }

    /**
     * 代码调用滑动关闭activity
     */
    override fun scrollToFinishActivity() {
        convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }

    /**
     * 右滑退出当前Activity方法
     *
     * @return 返回SwipeBackLayout布局
     */
    override fun getSwipeBackLayout(): SwipeBackLayout {
        // 获取swipeBackLayout（可以设置往右滑或者往左滑给I安比）
        return mSwipeHelper!!.swipeBackLayout
    }

    /**
     * 设置当前页面是否支持滑动删除Activity
     *
     * @param enable 是否支持滑动删除Activity
     */
    override fun setSwipeBackEnable(enable: Boolean) {
        // 设置滑动删除
        swipeBackLayout.setEnableGesture(enable)
    }
}