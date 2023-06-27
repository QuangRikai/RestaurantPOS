package com.example.restaurantpos.util

import android.content.Context
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.CartItemDAO
import com.example.restaurantpos.db.dao.CategoryDAO
import com.example.restaurantpos.db.dao.CustomerDAO
import com.example.restaurantpos.db.dao.ItemDAO
import com.example.restaurantpos.db.dao.OrderDAO
import com.example.restaurantpos.db.dao.TableDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase

object DatabaseUtil {

//    lateinit var appDAO: AppDAO
    lateinit var accountDAO: AccountDAO
    lateinit var categoryDAO: CategoryDAO
    lateinit var itemDAO: ItemDAO
    lateinit var tableDAO: TableDAO
    lateinit var cartItemDAO: CartItemDAO
    lateinit var orderDAO: OrderDAO
    lateinit var customerDAO: CustomerDAO

    fun init(context: Context){
//        appDAO = PosRoomDatabase.getInstance(context).appDAO()
        accountDAO = PosRoomDatabase.getInstance(context).accountDAO()
        categoryDAO = PosRoomDatabase.getInstance(context).categoryDAO()
        itemDAO = PosRoomDatabase.getInstance(context).itemDAO()
        tableDAO = PosRoomDatabase.getInstance(context).tableDAO()
        cartItemDAO = PosRoomDatabase.getInstance(context).cartItemDAO()
        orderDAO = PosRoomDatabase.getInstance(context).orderDAO()
        customerDAO = PosRoomDatabase.getInstance(context).customerDAO()
    }

    /** 1. USER MANAGEMENT  */

    fun addAccount(accountEntity: AccountEntity) = accountDAO.addAccount(accountEntity)

    fun addListAccount(listAccount: List<AccountEntity>) = accountDAO.addListAccount(listAccount)
    fun getAllUser() = accountDAO.getAllUser()

    /** 2. CATEGORY, ITEM MANAGEMENT  */

    fun getAllCategory() = categoryDAO.getAllCategory()
    fun addCategory(data: CategoryEntity) = categoryDAO.addCategory(data)
    fun addCategoryItem(data: ItemEntity) = itemDAO.addCategoryItem(data)
    fun addListCategoryItem(listData: List<ItemEntity>) = itemDAO.addListCategoryItem(listData)
    fun getListCategoryComponentItem(categoryComponentId: Int) = itemDAO.getListCategoryComponentItem(categoryComponentId)
    fun getItemOfCategory(item_id: Int) = itemDAO.getItemOfCategory(item_id)


    /** 3. TABLE MANAGEMENT  */
    fun addTable(data: TableEntity) = tableDAO.addTable(data)
    fun getAllTable() = tableDAO.getAllTable()


    /** 4. CART MANAGEMENT  */
    fun addCart(data : CartItemEntity) = cartItemDAO.addCartItem(data)
    fun addListCart(data : ArrayList<CartItemEntity>) = cartItemDAO.addListCartItem(data)
    fun deleteCart(data: CartItemEntity) = cartItemDAO.deleteCartItem(data)
    fun getListCart(order_id : String) = cartItemDAO.getListCartItem(order_id)

    // 2 hàm dưới làm gì?
    fun getListCartV0(order_id : String) = cartItemDAO.getListCartItemV0(order_id)
    fun getListCartOfKit(sortTime : Int) = cartItemDAO.getListCartItemOfKit(sortTime)

    /** 5. ORDER MANAGEMENT  */

    fun addOrder(data : OrderEntity) = orderDAO.addOrder(data)
    fun deleteOrder(data: OrderEntity) = orderDAO.deleteOrder(data)
    fun getOrder(order_id : String) = orderDAO.getOrder(order_id)
    fun getOrderByTable(table_id : Int)  = orderDAO.getOrderByTable(table_id)
    fun getListOrderOfCustomer(id : Int)  = orderDAO.getListOrderOfCustomer(id)

    /** 6. CUSTOMER MANAGEMENT  */
    fun addCustomer(data : CustomerEntity)  = customerDAO.addCustomer(data)

    fun deleteCustomer(data: CustomerEntity) = customerDAO.deleteCustomer(data)

    fun getCustomer(id : Int) = customerDAO.getCustomer(id)

    fun getListCustomer()  = customerDAO.getListCustomer()

    // Phục vụ cho việc tìm kiếm Khách
    fun getListCustomerByPhone(phone : String)  = customerDAO.getListCustomerByPhone(phone)



}