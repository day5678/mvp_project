package com.mvp.baselibrary.base

import android.os.Bundle
import dagger.android.AndroidInjection

/**
 * MVPActivity基类
 *
 * @auth wxf on 2018/11/14.
 */
abstract class BaseMVPActivity<P : BasePresenter<*>> : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        // 初始化dagger(必须放到super.onCreate前执行)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}