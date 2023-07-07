package com.example.restaurantpos.ui.manager.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentUpdateStaffInfoBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.util.DateFormatUtil
import com.example.restaurantpos.util.SharedPreferencesUtils


class UpdateStaffInfoFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    lateinit var binding: FragmentUpdateStaffInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateStaffInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(activity, MainManagerActivity::class.java)

        /** Khai báo viewModel --> Dùng phương thức addUser --> set ADD Button */
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        /** Device's Back Button*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        /** Cancel/Back */
        binding.txtCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val accountId: Int = SharedPreferencesUtils.getAccountId()
        viewModel.getAccountById(accountId)
            .observe(viewLifecycleOwner) { admin: MutableList<AccountEntity> ->
                binding.edtStaffName.hint = admin[0].account_name
                binding.edtUserName.hint = admin[0].user_name
                /** Update Button */
                binding.txtUpdate.setOnClickListener {
                    if (binding.edtStaffName.text.toString() != "") {
                        admin[0].account_name = binding.edtStaffName.text.toString()
                    }

                    if (binding.edtUserName.text.toString() != "") {
                        admin[0].user_name = binding.edtUserName.text.toString()
                    }

                    if (binding.edtPassword.text.toString() != "") {
                        admin[0].password = binding.edtPassword.text.toString()
                    }

                    viewModel.addUser(requireContext(), admin[0])
                    findNavController().popBackStack()
                }
            }

        /** ??? Vì sao chỗ này lại so sánh được với 1 */
        // Value = 1 --> Receptionist gửi.
        // Value = 2 --> Kitchen gửi.
        // Value thì mình chọn thôi

        /*
          1. Của User
          2. Của Kitchen
           */
        if (requireArguments().getInt("updateStaffInfo", 1) == 1) {

        }

        if (requireArguments().getInt("updateStaffInfo", 1) == 2){

        }
    }
}