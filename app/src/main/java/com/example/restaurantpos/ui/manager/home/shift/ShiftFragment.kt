package com.example.restaurantpos.ui.manager.home.shift

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentShiftBinding
import com.example.restaurantpos.db.entity.AccountShiftEntity
import com.example.restaurantpos.util.DataUtil
import java.util.Calendar


class ShiftFragment : Fragment() {
    private lateinit var binding: FragmentShiftBinding
    private lateinit var adapterShift: ShiftAdapter

    private lateinit var viewModelShift: ShiftViewModel

    private var year = 2023
    private var month = 7
    private var day = 4


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
        /*        viewModelShift.getListAccountShift(DateFormatUtil.getShiftId(year, month))
            .observe(viewLifecycleOwner) {
                adapterShift.setListData()
            }
            // Sao cứ phải làm việc khó khăn thế này. Xử lý bà nó trong Adapter luôn đi
            15-45:00
            */
        /**---------------------------------------------------------------------------------------*/

        // Vừa vào là đã phải hiển thị NOW TIME rồi nhé
        binding.txtDateInShift.text = "$year/$month"
        day = getFirst()

        /** imgBack */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        /** imgMonthBack */
        binding.imgMonthBack.setOnClickListener {
//            year = viewModelShift.minusDayReturnYear(year, month, day, 7)
//            month = viewModelShift.minusDayReturnMonth(year, month, day, 7)
//            day = viewModelShift.minusDayReturnDay(year, month, day, 7)

            minusDay()
        }

        /** imgMonthNext */
        binding.imgMonthNext.setOnClickListener {
//            year = viewModelShift.plusDayReturnYear(year, month, day, 7)
//            month = viewModelShift.plusDayReturnMonth(year, month, day, 7)
//            day = viewModelShift.plusDayReturnDay(year, month, day, 7)

            plusDay()

        }

        /**---------------------------------------------------------------------------------------*/
        /** Adapter */
        adapterShift = ShiftAdapter(
            requireContext(),
            viewLifecycleOwner,
            year,
            month,
            day,
            object : ShiftAdapter.EventClickShiftListener {
                override fun clickMorningShift(shift_id: String) {
                    viewModelShift.addAccountShift(AccountShiftEntity(1, shift_id, 1))
                    viewModelShift.addAccountShift(AccountShiftEntity(2, shift_id, 2))
                    viewModelShift.addAccountShift(AccountShiftEntity(3, shift_id, 3))
                }

                override fun clickAfternoonShift(shift_id: String) {
                    viewModelShift.addAccountShift(AccountShiftEntity(4, shift_id, 1))
                    viewModelShift.addAccountShift(AccountShiftEntity(5, shift_id, 2))
                    viewModelShift.addAccountShift(AccountShiftEntity(6, shift_id, 3))
                }

                override fun clickNightShift(shift_id: String) {
                    viewModelShift.addAccountShift(AccountShiftEntity(7, shift_id, 1))
                    viewModelShift.addAccountShift(AccountShiftEntity(8, shift_id, 2))
                    viewModelShift.addAccountShift(AccountShiftEntity(9, shift_id, 3))
                }
            })
        binding.rcyShift.adapter = adapterShift
        /**---------------------------------------------------------------------------------------*/
    }

    private fun minusDay() {
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
                day = DataUtil.numberOfDayInAMonthOfNotLeapYear[month - 1] + day - 7
            }
        } else {
            day -= 7
        }
        binding.txtDateInShift.text = "$year/$month"
        adapterShift.setListData(year, month, day)
    }

    private fun plusDay() {
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
        adapterShift.setListData(year, month, day)
    }

    /** getFirst --> Monday is ? */
    private fun getFirst(): Int {
        // Tạo một đối tượng Calendar
        val calendar = Calendar.getInstance()
        var today = calendar.get(Calendar.DATE) // Thứ 3
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // Thứ trong này


        // Xử lý để thứ 2 là ngày đầu tuần
        when (dayOfWeek) {
            1 -> {
                return today - 1
            }
            2 -> {
                return today - 2
            }

            3 -> {
                return today - 3
            }

            4 -> {
                return today - 4
            }

            5 -> {
                return today - 5
            }

            6 -> {
                return today - 6
            }

            7 -> {
                return today - 6
            }

        }
        return 2
    }
}