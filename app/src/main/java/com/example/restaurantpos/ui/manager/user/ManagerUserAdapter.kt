package com.example.restaurantpos.ui.manager.user

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.AccountEntity

class ManagerUserAdapter(
    var context: Context,
    private var listData: MutableList<AccountEntity>,
    val listenerClickEditUser: EventClickItemUserListener
) : RecyclerView.Adapter<ManagerUserAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtUserName = view.findViewById<TextView>(R.id.txtUserName)
        var txtUserRole = view.findViewById<TextView>(R.id.txtUserName)
        var imgEditUser = view.findViewById<ImageView>(R.id.imgEditUser)
    }

    // Tạo View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    // Hiển thị Data cho View
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemUser = listData[position]

        holder.txtUserName.text = itemUser.account_name
        holder.txtUserRole.text = if (itemUser.role == 1) {
            "Receptionist"
        } else {
            "Kitchen"
        }

        holder.imgEditUser.setOnClickListener {
            listenerClickEditUser.clickEditUser(itemUser)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData: MutableList<AccountEntity>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    interface EventClickItemUserListener {
        fun clickEditUser(itemUser: AccountEntity  )
    }
}