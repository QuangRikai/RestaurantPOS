package com.example.restaurantpos.ui.receptionist.table

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.TableEntity

class TableAdapter(
    var context: Context,
    private var listData: MutableList<TableEntity>,
    val listenerClickEditUser: EventClickTableListener
) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTableName = itemView.findViewById<TextView>(R.id.txtTableName)
        var viewRootTableItem = itemView.findViewById<LinearLayout>(R.id.viewRootTableItem)
    }

    //Method 1: Main in Adapter: XML Layout ==> View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val convertedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_table, parent, false)
        return ViewHolder(convertedView)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    // Method 2: Bind Each Element in List RESOURCE DATA (OutData Format) ==> Element in designed Layout ==> Display in Screen
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTable = listData[position]

        holder.txtTableName.text = itemTable.table_name

        holder.viewRootTableItem.setOnClickListener {
            listenerClickEditUser.clickTable(itemTable)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData: MutableList<TableEntity>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    interface EventClickTableListener {
        fun clickTable(itemUser: TableEntity)
    }
}