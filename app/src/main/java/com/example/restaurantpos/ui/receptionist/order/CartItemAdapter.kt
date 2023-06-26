package com.example.restaurantpos.ui.receptionist.order

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.CartEntity
import com.example.restaurantpos.util.DatabaseUtil


/** Adapter mày: CategoryItem --> ItemOfCategory ở phía dưới OrderFragment*/
class CartItemAdapter(
    var context: Context,
    private var listData: MutableList<CartEntity>,
    var lifecycleOwner: LifecycleOwner,
    val listenerClickOrderItem: EventClickCartItemListener

) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    // class ViewHolder --> đại diện cho mỗi item view trong RecyclerView.
    // Thường chứa các thành phần của View --> Để hiển thị cho mỗi item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewRootOrderItem: LinearLayout = itemView.findViewById(R.id.viewRootOrderItem)
        var imgCategoryItemImage = itemView.findViewById<ImageView>(R.id.imgCategoryItemImage)
        var txtItemName = itemView.findViewById<TextView>(R.id.txtItemName)
        var imgMinus = itemView.findViewById<ImageView>(R.id.imgMinus)
        var txtCount = itemView.findViewById<TextView>(R.id.txtCount)
        var imgPlus = itemView.findViewById<ImageView>(R.id.imgPlus)
        var txtNote = itemView.findViewById<TextView>(R.id.txtNote)
        var btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
    }

    //Method 1: Main in Adapter: XML Layout ==> View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val convertedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_item_in_cart, parent, false)
        return ViewHolder(convertedView)
    }

    override fun getItemCount() = listData.size


    // Method 2: Bind Each Element in List RESOURCE DATA (OutData Format) ==> Element in designed Layout ==> Display in Screen
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = listData[position]
        /** Đổ Data cho OrderItem */
//        holder.txtItemName.text = itemOrderedItem.
//        viewHolder.txtNote.text = item.note
//        viewHolder.txtCount.text = item.count.toString()


        /** Click Minus Item */
        holder.imgMinus.setOnClickListener {
            listenerClickOrderItem.clickMinus(cartItem, position)
        }


        /** Click Plus Item */
        holder.imgPlus.setOnClickListener {
            listenerClickOrderItem.clickPlus(cartItem, position)
        }


        /** Add Note for Item */
        holder.txtNote.setOnClickListener {
            listenerClickOrderItem.clickNote(cartItem, position)
        }

        /** Delete OrderedItem From CART */
        holder.btnDelete.setOnClickListener {
            listenerClickOrderItem.clickDelete(cartItem, position)
        }

        /** Get Info of Item --> Set for OrderedItem  <-- By itemOrderedItem.item_id */
        showInforItem(holder.txtItemName, holder.imgCategoryItemImage, cartItem.item_id)

    }

    private fun showInforItem(name : TextView, imgItem : ImageView, itemId: Int) {
        DatabaseUtil.getListCategoryComponentItem(itemId).observe(lifecycleOwner) { item ->
            name.text = item[0].item_name
            imgItem.setImageBitmap(BitmapFactory.decodeFile(item[0].image))
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newListData: MutableList<CartEntity>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    interface EventClickCartItemListener {

        fun clickMinus(orderedItem: CartEntity, pos: Int)
        fun clickPlus(orderedItem: CartEntity, pos: Int)
        fun clickNote(orderedItem: CartEntity, pos: Int)

        fun clickDelete(orderedItem: CartEntity, pos: Int)
    }
}