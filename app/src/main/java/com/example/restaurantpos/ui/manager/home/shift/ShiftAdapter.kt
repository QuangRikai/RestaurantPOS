package com.example.restaurantpos.ui.manager.home.shift

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.util.DatabaseUtil

// Thằng này xử lý đâu đầu vãi
class ShiftAdapter(
    var context: Context,
    private val lifecycleOwner: LifecycleOwner,
//    private var day: Int,
    private var month: Int,
    private var year: Int,
    val listenerClickShift: EventClickShiftListener
) : RecyclerView.Adapter<ShiftAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtDay = itemView.findViewById<TextView>(R.id.txtDay)
        var txtMorningShift = itemView.findViewById<TextView>(R.id.txtMorningShift)
        var txtAfternoonShift = itemView.findViewById<TextView>(R.id.txtAfternoonShift)
        var txtNightShift = itemView.findViewById<TextView>(R.id.txtNightShift)
    }

//    override fun getItemCount(): Int = 7

    override fun getItemCount(): Int {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)){
            return numberOfDayInAMonthOfLeapYear[month]
        } else{
            return numberOfDayInAMonthOfNotLeapYear[month]
        }

    }

    // Mỗi tháng có bao nhiêu ngày, còn cả vụ nhăm nhuận và start từ 0
    val numberOfDayInAMonthOfNotLeapYear =
        listOf<Int>(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val numberOfDayInAMonthOfLeapYear =
        listOf<Int>(0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    //Method 1: Main in Adapter: XML Layout ==> View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val convertedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shift, parent, false)
        return ViewHolder(convertedView)
    }

    // Method 2: Bind Each Element in List RESOURCE DATA (OutData Format) ==> Element in designed Layout ==> Display in Screen
    // Position ở đây sẽ ứng dụng position của List trong Database
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        /** Đổ data lên View */
        holder.txtDay.text = "${position + 1}"

        holder.txtMorningShift.setOnClickListener {
//            listenerClickShift.clickMorningShift(cartItem)
        }

        holder.txtAfternoonShift.setOnClickListener {
//            listenerClickShift.clickMorningShift(cartItem)
        }

        holder.txtNightShift.setOnClickListener {
//            listenerClickShift.clickMorningShift(cartItem)
        }

        /*        showInfo(
                    holder.txtTime,
                    holder.txtItemName,
                    holder.txtTableName,
                    holder.txtCartItemStatus,
                    cartItem.cart_item_status,
                    cartItem.order_id,
                    cartItem.item_id
                )*/
    }


    /**-----------------------------------------------------------------------------------------*/

    // Những thông tin về Table, Item thì phải get bằng order_id, item_id nha
    /* fun showInfo(
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
             *//* 0 -> txtCartItemStatus.text = "Wait"
             else -> txtCartItemStatus.text = "In Process"*//*

            // Xong là xong của Bếp
            0 -> txtCartItemStatus.text = "Wait"
            1 -> txtCartItemStatus.text = "In Process"
            2 -> txtCartItemStatus.text = "Done"
        }
    }*/

    // Làm sao để từ order_id lấy table_id--> Lấy table_name ?
    fun getTableName(txtTableName: TextView, table_id: Int) {
        DatabaseUtil.getTableById(table_id).observe(lifecycleOwner) { table ->
            txtTableName.text = table.table_name
        }
    }

/*    @SuppressLint("NotifyDataSetChanged")
    fun setListData(day: Int, month: Int, year: Int) {


        notifyDataSetChanged()
    }*/

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(month: Int, year: Int) {
        this.month = month
        this.year = year
        notifyDataSetChanged()
    }


    interface EventClickShiftListener {
        fun clickMorningShift(cartItemInKitchen: CartItemEntity)
        fun clickAfternoonShift(cartItemInKitchen: CartItemEntity)
        fun clickNightShift(cartItemInKitchen: CartItemEntity)
    }
}