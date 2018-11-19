package com.mvp.baselibrary.base

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * Presenter基类
 *
 * @auth wxf on 2018/11/15.
 */
abstract class BasePresenter<V: BaseView>(){
    lateinit var mView : V
    lateinit var provider : LifecycleProvider<*>
}