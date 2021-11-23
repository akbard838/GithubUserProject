package com.example.githubuserproject.ui

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserproject.R
import com.example.githubuserproject.base.BaseActivity
import com.example.githubuserproject.data.response.UserResponse
import com.example.githubuserproject.utils.Resource
import com.example.githubuserproject.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var userAdapter: UserAdapter

    private val userViewModel: CharactersViewModel by viewModels()

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
        userViewModel.characters.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) userAdapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->{
                    showToast(this, it.message)
                }
                Resource.Status.LOADING -> {

                }
            }
        })
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