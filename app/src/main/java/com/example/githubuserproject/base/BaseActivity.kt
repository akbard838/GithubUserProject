package com.example.githubuserproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserproject.utils.ContextProvider

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ContextProvider.initialize(applicationContext)

        setContentView(layoutResourceId)

        initIntent()
        initObservable()
        initUI()
        initAction()
        initProcess()
    }

    abstract fun initUI()
    abstract fun initIntent()
    abstract fun initAction()
    abstract fun initProcess()
    abstract fun initObservable()
}