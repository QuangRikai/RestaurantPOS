package com.example.restaurantpos.ui.manager.category

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.ItemEntity

class ManagerCategoryComponentAdapter(
    var context: Context,
    private var listData: MutableList<ItemEntity>,
    val listener: EventClickItemCategoryListener
) : RecyclerView.Adapter<ManagerCategoryComponentAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Item Components
        var imgCategoryItemImage = itemView.findViewById<ImageView>(R.id.imgCategoryItem)
        var txtItemName = itemView.findViewById<TextView>(R.id.txtItemName)
        var txtItemPrice = itemView.findViewById<TextView>(R.id.txtItemPrice)
        var txtItemInventoryQuantity = itemView.findViewById<TextView>(R.id.txtItemInventoryQuantity)
        // Add + ImageView
//        var imgAddCategoryItem = itemView.findViewById<ImageView>(R.id.imgAddCategoryItem)
        // Whole Item
        var viewItemCategory = itemView.findViewById<LinearLayout>(R.id.viewItemCategory)

        // A Lot of Images of Item
//        var vpgCategory = itemView.findViewById<ViewPager>(R.id.vpgCategory)

    }

    //Method 1: Main in Adapter: XML Layout ==> View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val convertedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manager_category_component, parent, false)
        return ViewHolder(convertedView)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    // Method 2: Bind Each Element in List RESOURCE DATA (OutData Format) ==> Element in designed Layout
    // ==> Display in Screen And ==> Make Actions on it
    /**
     * Khác nhau ở mớ này là nhiều
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCategory = listData[position]

        // Set data from Database for Item
//        holder.imgCategoryItemImage.setImageResource(R.drawable.img_cake_coffee)
        holder.txtItemName.text = itemCategory.item_name
        holder.txtItemPrice.text = itemCategory.price.toString()
        holder.txtItemInventoryQuantity.text = itemCategory.inventory_quantity.toString()



        // User the Interface below

/*          holder.imgAddCategoryItem.setOnClickListener {
            listener.clickCategoryItem(itemCategory)
        }*/

        holder.viewItemCategory.setOnLongClickListener {
            listener.longClickCategoryItem(itemCategory)
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData: MutableList<ItemEntity>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    interface EventClickItemCategoryListener {
//        fun clickCategoryItem(itemCategory: ItemEntity)
        fun longClickCategoryItem(itemCategory: ItemEntity)

    }
}