package com.example.githubuserproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserproject.R
import com.example.githubuserproject.data.response.UserResponse
import com.example.githubuserproject.utils.emptyString
import com.example.githubuserproject.utils.setImagePath

class UserAdapter(
    private val users: ArrayList<UserResponse>,
    val listener: OnUserItemListener? = null
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = users[position]

        holder.apply {
            tvName.text = data.login
            tvId.text = data.id.toString()
            imgUser.setImagePath(
                holder.itemView.context,
                data.avatar_url ?: emptyString(),
                pbUser,
                0
            )
            itemView.setOnClickListener {
                listener?.onUserItemClicked(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser = itemView.findViewById<AppCompatImageView>(R.id.imgUser)
        val tvName = itemView.findViewById<AppCompatTextView>(R.id.tvName)
        val tvId = itemView.findViewById<AppCompatTextView>(R.id.tvId)
        val pbUser = itemView.findViewById<ProgressBar>(R.id.pbUser)
    }

    interface OnUserItemListener {
        fun onUserItemClicked(user: UserResponse)
    }
}