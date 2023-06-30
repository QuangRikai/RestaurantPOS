package com.example.restaurantpos.ui.kitchen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.util.DatabaseUtil

class CartItemInKitchenAdapter(
    var context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private var listData: MutableList<CartItemEntity>,
    val listenerClickCartItemInKitchen: EventClickCartItemInKitchenListener
) : RecyclerView.Adapter<CartItemInKitchenAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewRootCartItemInKitchen =
            itemView.findViewById<LinearLayout>(R.id.viewRootCartItemInKitchen)
        var txtNo = itemView.findViewById<TextView>(R.id.txtNo)
        var txtTime = itemView.findViewById<TextView>(R.id.txtTime)
        var txtTableName = itemView.findViewById<TextView>(R.id.txtTableName)
        var txtItemName = itemView.findViewById<TextView>(R.id.txtItemName)
        var txtNote = itemView.findViewById<TextView>(R.id.txtNote)
        var txtCartItemStatus = itemView.findViewById<TextView>(R.id.txtCartItemStatus)
    }

    //Method 1: Main in Adapter: XML Layout ==> View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val convertedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_item_detail_in_kitchen, parent, false)
        return ViewHolder(convertedView)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    // Method 2: Bind Each Element in List RESOURCE DATA (OutData Format) ==> Element in designed Layout ==> Display in Screen
    // Position ở đây sẽ ứng dụng position của List trong Database
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = listData[position]
        /** Đổ data lên View */
        holder.txtNo.text = "${position + 1}"
        holder.txtNote.text = cartItem.note

        holder.txtCartItemStatus.setOnClickListener {
            listenerClickCartItemInKitchen.clickCartItemStatus(cartItem)
        }

        showInfo(
            holder.txtTime,
            holder.txtItemName,
            holder.txtTableName,
            holder.txtCartItemStatus,
            cartItem.cart_item_status,
            cartItem.order_id,
            cartItem.item_id
        )
    }

    // Những thông tin về Table, Item thì phải get bằng order_id, item_id nha
    fun showInfo(
        txtTime: TextView,
        txtItemName: TextView,
        txtTableName: TextView,
        txtCartItemStatus: TextView,
        status: Int,
        order_id: String,
        item_id: Int
    ) {

        DatabaseUtil.getOrder(order_id).observe(lifecycleOwner) { order ->
            // Order
            txtTime.text = order.order_create_time.substring(12, order.order_create_time.length)
            // Table
            getTableName(txtTableName, order.table_id)
        }
        // Item
        DatabaseUtil.getItemOfCategory(item_id).observe(lifecycleOwner) { itemList ->
            txtItemName.text = itemList[0].item_name
        }

        when (status) {
            /* 0 -> txtCartItemStatus.text = "Wait"
             else -> txtCartItemStatus.text = "In Process"*/

            // Xong là xong của Bếp
            0 -> txtCartItemStatus.text = "Wait"
            1 -> txtCartItemStatus.text = "In Process"
            2 -> txtCartItemStatus.text = "Done"
        }
    }

    // Làm sao để từ order_id lấy table_id--> Lấy table_name ?
    fun getTableName(txtTableName: TextView, table_id: Int) {
        DatabaseUtil.getTableById(table_id).observe(lifecycleOwner) { table ->
            txtTableName.text = table.table_name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData: MutableList<CartItemEntity>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }


    interface EventClickCartItemInKitchenListener {
        fun clickCartItemStatus(cartItemInKitchen: CartItemEntity)
    }
}