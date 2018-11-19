package com.mvp.baselibrary.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import java.util.*

/**
 * Activity管理类
 *
 * @auth wxf on 2018/11/14.
 */
class APPActivityManager private constructor() {
    // 管理activity的堆栈数组
    private var activityStack: Stack<AppCompatActivity> = Stack()

    /**
     * 双重校验锁单例模式
     */
    companion object {
        val instance: APPActivityManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            APPActivityManager()
        }
    }

    /**
     * 对activity进行入堆栈管理
     *
     * @param activity
     *          需要入栈的activity
     */
    fun addActivity(activity: AppCompatActivity) {
        // 添加进堆栈
        activityStack.add(activity)
    }

    /**
     * 对activity进行出堆栈管理
     *
     * @param activity
     *          需要出堆栈的activity
     */
    fun finishActivity(activity: AppCompatActivity) {
        // 关闭activity
        activity.finish()
        // 从堆栈中移出
        activityStack.remove(activity)
    }

    /**
     * 关闭所有activity
     */
    private fun finishAllActivity() {
        // 循环遍历堆栈
        for (activity in activityStack) {
            // 关闭遍历到的activity
            activity.finish()
        }
        // 清除堆栈
        activityStack.clear()
    }

    /**
     * 关闭其他所有activity
     *
     * @param activity
     *          不被关闭的activity
     */
    fun finishAnotherActivity(activity: AppCompatActivity) {
        // 循环遍历堆栈
        for (stackActivity in activityStack) {
            // 判断是否为
            if(activity != stackActivity){
                // 关闭遍历到的activity
                activity.finish()
            }
        }
    }

    /**
     * 退出app
     *
     * @param context
     *          上下文
     */
    fun exitApp(context: Context) {
        try{
            // 关闭所有activity
            finishAllActivity()
            // 调用系统的关闭后台运行进程的方法
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).killBackgroundProcesses(context.packageName)
            // 调用系统的退出方法
            System.exit(0)
        } catch (e : Exception){
            Logger.e("exitApp error")
        }
    }


    /**
     * 获取当前栈顶Activity
     *
     * @return 当前activity
     */
    fun currentActivity(): AppCompatActivity? {
        return activityStack.peek()
    }
}