package com.example.restaurantpos.ui.staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentShiftOfStaffBinding
import com.example.restaurantpos.ui.manager.home.shift.ShiftViewModel
import com.example.restaurantpos.util.DateFormatUtil
import java.util.Calendar

class ShiftOfStaffFragment : Fragment() {
    lateinit var binding: FragmentShiftOfStaffBinding
    lateinit var viewModelShift: ShiftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShiftOfStaffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelShift = ViewModelProvider(this).get(ShiftViewModel::class.java)

        /** Device's Back Button*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        /** Xử lý Shift_ID */
        val calendar = Calendar.getInstance()

        val nowYear = calendar.get(Calendar.YEAR)
        val nowMonth = calendar.get(Calendar.MONTH) + 1
        val nowDay = calendar.get(Calendar.DATE)
        val hour = calendar.get(Calendar.HOUR)
        val shiftName = if (hour >= 18) {
            3
        } else if (hour >= 12) {
            2
        } else {
            1
        }
        /** ??? Vì sao chỗ này lại so sánh được với 1 */
        // Value = 1 --> Receptionist gửi.
        // Value = 2 --> Kitchen gửi.
        // Value thì mình chọn thôi

        /*
          1. Của User
          2. Của Kitchen
           */
        if (requireArguments().getInt("shiftOfStaff", 1) == 1) {
            val shiftID = DateFormatUtil.getShiftId(nowYear, nowMonth, nowDay, shiftName)
            viewModelShift.getListAccountShiftReceptionist(shiftID).observe(viewLifecycleOwner) {
                binding.txtTitle.text = it.toString()
            }
        }

        if (requireArguments().getInt("shiftOfStaff", 1) == 2){
            val shiftID = DateFormatUtil.getShiftId(nowYear, nowMonth, nowDay, shiftName)
            viewModelShift.getListAccountShiftKitchen(shiftID).observe(viewLifecycleOwner) {
                binding.txtTitle.text = it.toString()
            }
        }


    }
}