package com.example.githubuserproject.ui

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserproject.R
import com.example.githubuserproject.base.BaseActivity
import com.example.githubuserproject.data.response.UserResponse
import com.example.githubuserproject.utils.ApiResponse
import com.example.githubuserproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var userAdapter: UserAdapter

    private val userViewModel: UserViewModel by viewModels()

    private var users: ArrayList<UserResponse> = arrayListOf()

    override val layoutResourceId: Int = R.layout.activity_main

    override fun initIntent() {
    }

    override fun initUI() {
        initRecyclerView()
    }

    override fun initAction() {
    }

    override fun initProcess() {
    }

    override fun initObservable() {
        lifecycleScope.launchWhenStarted {
            userViewModel.getUsers(0, 20).collect {
                when(it){
                    is ApiResponse.Success -> {
                        it.data?.let { datas ->
                            users.clear()
                            users.addAll(datas)
                            userAdapter.notifyDataSetChanged()
                        }

                    }
                    is ApiResponse.Failure -> {
                        showToast(this@MainActivity, it.message)
                    }
                    is ApiResponse.Loading -> {

                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(users)

        rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }
}