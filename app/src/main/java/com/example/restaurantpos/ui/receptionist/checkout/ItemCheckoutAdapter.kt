package com.example.restaurantpos.ui.receptionist.checkout
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.lifecycle.LifecycleOwner
//import androidx.recyclerview.widget.RecyclerView
//import com.example.restaurantpos.R
//import com.example.restaurantpos.db.entity.CartItemEntity
//import com.example.restaurantpos.util.DatabaseUtil
//
//class ItemCheckoutAdapter (
//    var context: Context,
//    private var listData: ArrayList<CartItemEntity>,
//    var owner: LifecycleOwner,
//    val listener: EventClickCartOnBillListener
//) : RecyclerView.Adapter<ItemCheckoutAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var txtStt: TextView = view.findViewById(R.id.txtStt)
//        var txtItemName: TextView = view.findViewById(R.id.txtItemName)
//        var txtNote: TextView = view.findViewById(R.id.txtNote)
//        var txtCondition: TextView = view.findViewById(R.id.txtCondition)
//        var viewRoot: LinearLayout = view.findViewById(R.id.viewRoot)
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.item_cart_on_bill, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    @SuppressLint("UseCompatLoadingForDrawables")
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val item = listData[position]
//
//        viewHolder.txtStt.text = "${position + 1}"
//        viewHolder.txtNote.text = item.note
//
//        showInfor(
//            viewHolder.txtItemName,
//            viewHolder.txtCondition,
//            item.status,
//            item.item_id
//        )
//
//        viewHolder.txtCondition.setOnClickListener {
//            if (item.status == 2) {
//                listener.clickCondition(item)
//            }
//
//        }
//
//    }
//
//
//    fun showInfor(
//        txtItemName: TextView,
//        txtCondition: TextView,
//        condition: Int,
//        item_id: Int
//    ) {
//
//        DatabaseUtil.getItemOfCategory(item_id).observe(owner) {
//            txtItemName.text = it[0].name
//        }
//
//
//        when (condition) {
//            0 -> {
//                txtCondition.text = "Chờ"
//            }
//            1 -> {
//                txtCondition.text = "Đang làm"
//            }
//            2 -> {
//                txtCondition.text = "Làm xong"
//            }
//            3 -> {
//                txtCondition.text = "Đã phục vụ"
//            }
//        }
//    }
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setListData(arr: ArrayList<CartItemEntity>) {
//        listData.clear()
//        listData.addAll(arr)
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount() = listData.size
//
//}
//
//interface EventClickCartOnBillListener {
//    fun clickCondition(cartEntity: CartItemEntity)
//}