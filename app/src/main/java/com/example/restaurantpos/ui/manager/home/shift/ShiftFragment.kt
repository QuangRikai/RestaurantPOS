package com.example.restaurantpos.ui.manager.home.shift

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentShiftBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.ui.manager.user.UserViewModel
import com.example.restaurantpos.util.DataUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import java.util.Calendar


class ShiftFragment : Fragment() {
    private lateinit var binding: FragmentShiftBinding
    private lateinit var adapterShift: ShiftAdapter
    private lateinit var adapterStaffSelection: StaffSelectionAdapter

    private lateinit var viewModelShift: ShiftViewModel
    private lateinit var viewModelUser: UserViewModel


    private var year = 2023
    private var month = 7
    private var day = 3

    // Add account_shift
    lateinit var dialog: AlertDialog


    private var staffObject: AccountEntity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** ViewModel */
        viewModelShift = ViewModelProvider(this).get(ShiftViewModel::class.java)
        viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        /*        viewModelShift.getListAccountShift(DateFormatUtil.getShiftId(year, month))
            .observe(viewLifecycleOwner) {
                adapterShift.setListData()
            }
            // Sao cứ phải làm việc khó khăn thế này. Xử lý bà nó trong Adapter luôn đi
            15-45:00
            */
        /**---------------------------------------------------------------------------------------*/

        // Vừa vào là đã phải hiển thị NOW TIME (year + month + day) rồi nhé
        binding.txtDateInShift.text = "$year/$month"
        day = getFirst()

        /** imgBack */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        /** imgMonthBack */
        binding.imgMonthBack.setOnClickListener {
            backWeek()
        }

        /** imgMonthNext */
        binding.imgMonthNext.setOnClickListener {
            nextWeek()
        }

        /**---------------------------------------------------------------------------------------*/
        /** Adapter */
        adapterShift = ShiftAdapter(
            requireContext(),
            viewLifecycleOwner,
            ArrayList(),
            year,
            month,
            day,
            object : ShiftAdapter.EventClickShiftListener {
                override fun clickMorningShift(shift_id: String) {
                    if (SharedPreferencesUtils.getAccountRole() == 0) {
                        showAddAccountShiftDialog(shift_id)
                    }
                }

                override fun clickAfternoonShift(shift_id: String) {
                    if (SharedPreferencesUtils.getAccountRole() == 0) {
                        showAddAccountShiftDialog(shift_id)
                    }
                }

                override fun clickNightShift(shift_id: String) {
                    if (SharedPreferencesUtils.getAccountRole() == 0) {
                        showAddAccountShiftDialog(shift_id)
                    }
                }
            })
        binding.rcyShift.adapter = adapterShift
        /**---------------------------------------------------------------------------------------*/
    }

    /** Add Accouont_Shift Dialog */
    // Copy từ NewOrderFragment sang
    private fun showAddAccountShiftDialog(shift_id: String) {
// -----------------Prepare--------------------------------------------------//
        // 1.  Build Dialog
        // 2.  Designed XML --> View
        // 3.  Set VIEW tra ve above --> Dialog
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        val view = layoutInflater.inflate(R.layout.dialog_alert_add_account_shift, null)
        build.setView(view)
        // 4.  Get Component of Dialog
        val edtShiftYear = view.findViewById<TextView>(R.id.edtShiftYear)
        val edtShiftMonth = view.findViewById<TextView>(R.id.edtShiftMonth)
        val edtShiftDay = view.findViewById<TextView>(R.id.edtShiftDay)
        val edtShiftName = view.findViewById<TextView>(R.id.edtShiftName)
        val edtStaffName = view.findViewById<EditText>(R.id.edtStaffName)

        val rcyStaffSelection = view.findViewById<RecyclerView>(R.id.rcyStaffSelection)

        val btnAddStaffShift = view.findViewById<Button>(R.id.btnAddStaffShift)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val imgClose = view.findViewById<ImageView>(R.id.imgCloseDialogCustomer)
        // -----------------Code for Component----------------------------------------//
        /*          // rcyStaffSelection
                  adapterStaffSelection = StaffSelectionAdapter(requireParentFragment(), ArrayList(), object :
                      StaffSelectionAdapter.EventClickStaffListener {
                      override fun clickStaff(itemStaff: AccountEntity) {
                          staffObject = itemStaff

                      }
                  })

                  rcyStaffSelection.adapter = adapterStaffSelection

                  // 2. Code for when staff types on edtPhoneNumber and contain >= 3 Chars. If exist --> Show for Picking-up
                  // SetData for (1)
                  edtStaffName.doOnTextChanged { text, start, before, count ->
                      if (text.toString().isNotEmpty()) {
                          viewModelUser.getStaffByName(text.toString())
                              .observe(viewLifecycleOwner) { staffByName ->
                                  if (staffByName.size > 0) {
                                      adapterStaffSelection.setListData(staffByName as ArrayList<AccountEntity>)
                                      rcyStaffSelection.show()
                                  }
                              }
                      } else {
                          rcyStaffSelection.gone()
                      }
                  }


                  val shiftID = DateFormatUtil.getShiftId(
                      edtShiftYear.text.toString().toInt(),
                      edtShiftMonth.text.toString().toInt(),
                      edtShiftDay.text.toString().toInt(),
                      edtShiftName.text.toString().toInt()
                  )

                  // 4.  Add AccountShift
                  btnAddStaffShift.setOnClickListener {
                      viewModelShift.addAccountShift(
                          AccountShiftEntity(
                              0, shiftID, staffObject!!.account_id
                          )
                      )
                                  viewModelShift.getListAccountShiftForSetListData(shiftID).observe(viewLifecycleOwner) {
                                    adapterShift.setListData(it)
                                }
                }*/

        // Other:  Dau X  &   Cancel Button
        imgClose.setOnClickListener { dialog.dismiss() }
        btnCancel.setOnClickListener { dialog.dismiss() }

        // End: Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }

    private fun backWeek() {
        if (day <= 7) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                if (month == 1) {
                    month = 12
                    year -= 1
                } else {
                    month -= 1
                }
                day = DataUtil.numberOfDayInAMonthOfLeapYear[month - 1] + (day - 7)
            } else {
                if (month == 1) {
                    month = 12
                    year -= 1
                } else {
                    month -= 1
                }
                day = DataUtil.numberOfDayInAMonthOfNotLeapYear[month - 1] + (day - 7)
            }
        } else {
            day -= 7
        }
        binding.txtDateInShift.text = "$year/$month"
        adapterShift.setDate(year, month, day)
    }

    private fun nextWeek() {
        val now = day + 7

        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            if (now > DataUtil.numberOfDayInAMonthOfLeapYear[month]) {
                day = now - DataUtil.numberOfDayInAMonthOfLeapYear[month]
                if (month == 12) {
                    month = 11
                    year += 1
                } else {
                    month += 1
                }
            } else {
                day = now
            }
        } else {
            if (now > DataUtil.numberOfDayInAMonthOfNotLeapYear[month]) {
                day = now - DataUtil.numberOfDayInAMonthOfNotLeapYear[month]
                if (month == 12) {
                    month = 11
                    year += 1
                } else {
                    month += 1
                }
            } else {
                day = now
            }
        }
        binding.txtDateInShift.text = "$year/$month"
        adapterShift.setDate(year, month, day)
    }

    /** getFirst --> Monday is ? */
    private fun getFirst(): Int {
        // Tạo một đối tượng Calendar, khởi tạo với thời gian hiện tại của hệ thống
        val calendar = Calendar.getInstance()
        // Trường DATE đại diện cho ngày trong tháng hiện tại.
        val nowDay = calendar.get(Calendar.DATE) // Ngày 7/5
        // Thứ trong tuần
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // Thứ 4


        // Xử lý để thứ 2 là ngày đầu tuần
        // Hiện tại 1 là Chủ Nhật
        when (dayOfWeek) {
            1 -> {
                return nowDay - 3
            }

            2 -> {
                return nowDay - 4
            }

            3 -> {
                return nowDay - 5
            }

            4 -> {
                return nowDay - 6
            }

            5 -> {
                return nowDay
            }

            6 -> {
                return nowDay - 1
            }

            7 -> {
                return nowDay - 2
            }

        }
        return 3
    }
}