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
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.util.DateFormatUtil


class ShiftFragment : Fragment() {
    private lateinit var binding: FragmentShiftBinding
    private lateinit var adapterShift: ShiftAdapter

    private lateinit var viewModelShift: ShiftViewModel

    private var month = 7
    private var year = 2023

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
        // Vừa vào là đã phải hiển thị NOW TIME rồi nhé
        binding.txtDateInShift.text = "$year/$month"

        /** imgBack */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        /** imgMonthBack */
        binding.imgMonthBack.setOnClickListener {
            if (month == 1) {
                month = 12
                year--
            } else {
                month--
            }
            binding.txtDateInShift.text = "$year/$month"
            adapterShift.setListData(month, year)
        }

        /** imgMonthNext */
        binding.imgMonthNext.setOnClickListener {
            if (month == 12) {
                month = 1
                year++
            } else {
                month++
            }
            binding.txtDateInShift.text = "$year/$month"
            adapterShift.setListData(month, year)
        }

        /**---------------------------------------------------------------------------------------*/
        /** Adapter */
        adapterShift = ShiftAdapter(
            requireContext(),
            viewLifecycleOwner,
            7,
            2023,
            object : ShiftAdapter.EventClickShiftListener {
                override fun clickMorningShift(cartItemInKitchen: CartItemEntity) {

                }

                override fun clickAfternoonShift(cartItemInKitchen: CartItemEntity) {

                }

                override fun clickNightShift(cartItemInKitchen: CartItemEntity) {

                }

            })
        binding.rcyShift.adapter = adapterShift
        /**---------------------------------------------------------------------------------------*/
        // ViewModel
        viewModelShift = ViewModelProvider(this).get(ShiftViewModel::class.java)

/*        viewModelShift.getListAccountShift(DateFormatUtil.getShiftId(year, month))
            .observe(viewLifecycleOwner) {
                adapterShift.setListData()
            }
            // Sao cứ phải làm việc khó khăn thế này. Xử lý bà nó trong Adapter luôn đi
            15-45:00
            */
    }
}