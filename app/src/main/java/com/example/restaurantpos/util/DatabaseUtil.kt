package com.example.restaurantpos.util

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.dao.CartItemDAO
import com.example.restaurantpos.db.dao.CategoryDAO
import com.example.restaurantpos.db.dao.CustomerDAO
import com.example.restaurantpos.db.dao.ItemDAO
import com.example.restaurantpos.db.dao.OrderDAO
import com.example.restaurantpos.db.dao.ShiftDAO
import com.example.restaurantpos.db.dao.TableDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.AccountShiftEntity
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.ShiftEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase

object DatabaseUtil {

    lateinit var appDAO: AppDAO
    lateinit var accountDAO: AccountDAO
    lateinit var categoryDAO: CategoryDAO
    lateinit var itemDAO: ItemDAO
    lateinit var tableDAO: TableDAO
    lateinit var cartItemDAO: CartItemDAO
    lateinit var orderDAO: OrderDAO
    lateinit var customerDAO: CustomerDAO
    lateinit var shiftDAO: ShiftDAO


    fun init(context: Context) {
        appDAO = PosRoomDatabase.getInstance(context).appDAO()
        accountDAO = PosRoomDatabase.getInstance(context).accountDAO()
        categoryDAO = PosRoomDatabase.getInstance(context).categoryDAO()
        itemDAO = PosRoomDatabase.getInstance(context).itemDAO()
        tableDAO = PosRoomDatabase.getInstance(context).tableDAO()
        cartItemDAO = PosRoomDatabase.getInstance(context).cartItemDAO()
        orderDAO = PosRoomDatabase.getInstance(context).orderDAO()
        customerDAO = PosRoomDatabase.getInstance(context).customerDAO()
        shiftDAO = PosRoomDatabase.getInstance(context).shiftDAO()
    }

    /** 1. USER MANAGEMENT  */

    fun addAccount(accountEntity: AccountEntity) = accountDAO.addAccount(accountEntity)

    fun addListAccount(listAccount: List<AccountEntity>) = accountDAO.addListAccount(listAccount)
    fun getAllUser() = accountDAO.getAllUser()

    /** 2. CATEGORY && ITEM MANAGEMENT  */

    fun getAllCategory() = categoryDAO.getAllCategory()
    fun addCategory(data: CategoryEntity) = categoryDAO.addCategory(data)
    fun addCategoryItem(data: ItemEntity) = itemDAO.addCategoryItem(data)
    fun addListCategoryItem(listData: List<ItemEntity>) = itemDAO.addListCategoryItem(listData)
    fun getListCategoryComponentItem(categoryComponentId: Int) =
        itemDAO.getListCategoryComponentItem(categoryComponentId)

    fun getItemOfCategory(item_id: Int) = itemDAO.getItemOfCategory(item_id)


    /** 3. TABLE MANAGEMENT  */
    fun addTable(data: TableEntity) = tableDAO.addTable(data)

    fun getTableById(table_id: Int) = tableDAO.getTableById(table_id)
    fun getAllTable() = tableDAO.getAllTable()


    /** 4. CART MANAGEMENT  */
    fun addCartItem(data: CartItemEntity) = cartItemDAO.addCartItem(data)
    fun addListCartItem(data: List<CartItemEntity>) = cartItemDAO.addListCartItem(data)
    fun deleteCart(data: CartItemEntity) = cartItemDAO.deleteCartItem(data)
    fun getListCartItemByOrderId(order_id: String) = cartItemDAO.getListCartItemByOrderId(order_id)

    // 2 hàm dưới làm gì?
    fun getListCartItemOfKitchen() = cartItemDAO.getListCartItemOfKitchen()
    fun getListCartItemOfKitchenBySortTime(sortByTimeOfOrder: Int) =
        cartItemDAO.getListCartItemOfKitchenBySortTime(sortByTimeOfOrder)

    fun getListCartItemOnWaiting(order_id: String) = cartItemDAO.getListCartItemOnWaiting(order_id)

    /** 5. ORDER MANAGEMENT  */

    fun addOrder(data: OrderEntity) = orderDAO.addOrder(data)
    fun deleteOrder(data: OrderEntity) = orderDAO.deleteOrder(data)
    fun getOrder(order_id: String) = orderDAO.getOrder(order_id)
    fun getOrderByTable(table_id: Int) = orderDAO.getOrderByTable(table_id)
    fun getListOrderByCustomerId(id: Int) = orderDAO.getListOrderByCustomerId(id)

    /** 6. CUSTOMER MANAGEMENT  */
    fun addCustomer(data: CustomerEntity) = customerDAO.addCustomer(data)

    fun deleteCustomer(data: CustomerEntity) = customerDAO.deleteCustomer(data)

    fun getCustomer(id: Int) = customerDAO.getCustomer(id)

    fun getListCustomer() = customerDAO.getListCustomer()

    // Phục vụ cho việc tìm kiếm Khách
    fun getListCustomerByPhoneForSearch(phone: String) =
        customerDAO.getListCustomerByPhoneForSearch(phone)

    fun getListCustomerByPhoneForAdd(phone: String) =
        customerDAO.getListCustomerByPhoneForAdd(phone)

    /** 7. Shift && ShiftAccount MANAGEMENT  */
    fun addShift(shift: ShiftEntity) = shiftDAO.addShift(shift)

    fun getTShiftById(shift_id: String) = shiftDAO.getTShiftById(shift_id)

    fun getListShift() = shiftDAO.getListShift()

    fun addAccountShift(accountShift: AccountShiftEntity) = shiftDAO.addAccountShift(accountShift)

    fun deleteAccountShift(accountShift: AccountShiftEntity) =
        shiftDAO.deleteAccountShift(accountShift)

    fun getListAccountShift(shift_id: String) = shiftDAO.getListAccountShift(shift_id)

}