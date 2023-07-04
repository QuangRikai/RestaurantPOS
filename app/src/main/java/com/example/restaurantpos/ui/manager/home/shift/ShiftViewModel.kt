package com.example.restaurantpos.ui.manager.home.shift

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.AccountShiftEntity
import com.example.restaurantpos.db.entity.ShiftEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShiftViewModel : ViewModel() {

    fun addShift(shift: ShiftEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.shiftDAO.addShift(shift)
        }
    }

    fun getShiftById(shift_id: String) = DatabaseUtil.getShiftById(shift_id)

    fun getListShift() = DatabaseUtil.getListShift()

    fun addAccountShift(accountShift: AccountShiftEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.shiftDAO.addAccountShift(accountShift)
        }
    }

    fun deleteAccountShift(accountShift: AccountShiftEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.shiftDAO.deleteAccountShift(accountShift)
        }
    }


    fun getListAccountShift(shift_id: String) = DatabaseUtil.getListAccountShift(shift_id)
    fun getListAccountShiftReceptionist(shift_id: String) = DatabaseUtil.getListAccountShiftReceptionist(shift_id)
    fun getListAccountShiftKitchen(shift_id: String) = DatabaseUtil.getListAccountShiftKitchen(shift_id)


}