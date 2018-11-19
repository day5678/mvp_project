package com.mvp.app

import android.content.Intent
import android.os.Bundle
import com.mvp.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            startActivity(Intent(this,Main2Activity::class.java))
        }
    }
}
