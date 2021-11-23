package com.example.githubuserproject.ui

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubuserproject.R
import com.example.githubuserproject.base.BaseActivity
import com.example.githubuserproject.data.response.UserResponse
import com.example.githubuserproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, UserAdapter.OnUserItemListener {

    private lateinit var userAdapter: UserAdapter

    private lateinit var layoutManager: LinearLayoutManager

    private val userViewModel: UserViewModel by viewModels()

    private var users: ArrayList<UserResponse> = arrayListOf()

    private var isLoading = false

    private var sinceId = 0

    private var isLast = false

    override val layoutResourceId: Int = R.layout.activity_main

    override fun initIntent() {
    }

    override fun initUI() {
        layoutManager = LinearLayoutManager(this@MainActivity)
        srlMain.setOnRefreshListener(this)
        initRecyclerView()
        getUsers(false)
        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = userAdapter.itemCount
                if (!isLoading && !isLast) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        sinceId += 10
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) pbMain.visible()

        lifecycleScope.launchWhenStarted {
            userViewModel.getUsers(sinceId).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        llError.gone()
                        llMain.visible()
                        it.data?.let { datas ->
                            users.addAll(datas)
                            userAdapter.notifyDataSetChanged()
                        }

                        if (it.data == null || it.data.isEmpty()) {
                            pbMain.gone()
                            isLast = true
                        } else pbMain.invisible()

                        isLoading = false
                        srlMain.isRefreshing = false
                    }
                    is ApiResponse.Failure -> {
                        llError.visible()
                        llMain.gone()
                        showToast(this@MainActivity, it.message)
                    }
                    is ApiResponse.Loading -> {

                    }
                }
            }
        }


    }

    override fun initAction() {
        btnRetry.setOnClickListener {
            loadDefault()
        }
    }

    override fun initProcess() {
    }

    override fun initObservable() {

    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter(users, this)

        rvUser.setHasFixedSize(true)
        rvUser.layoutManager = layoutManager
        rvUser.adapter = userAdapter
    }

    private fun loadDefault() {
        users.clear()
        userAdapter.notifyDataSetChanged()
        sinceId = 0
        getUsers(true)
    }

    override fun onUserItemClicked(user: UserResponse) {
        showToast(this, user.login)
    }

    override fun onRefresh() {
        loadDefault()
    }
}