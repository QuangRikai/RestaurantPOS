package com.example.restaurantpos.ui.manager.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.base.BaseFragment
import com.example.restaurantpos.databinding.FragmentManagerUserBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.util.showToast

class ManagerUserFragment : BaseFragment<FragmentManagerUserBinding>() {

    private lateinit var viewModel: UserViewModel
    lateinit var adapter: ManagerUserAdapter
    lateinit var dialog: AlertDialog

    /**
     * Show Account List onto Screen
     */
    override fun initCreate() {
        adapter = ManagerUserAdapter(
            requireActivity(),
            ArrayList(),
            object : ManagerUserAdapter.EventClickItemUserListener {
                override fun clickEditUser(itemUser: AccountEntity) {
                    showEditDialog(itemUser)
                }
            })
        binding.rcyUserManagement.adapter = adapter

        /**  Set data getAllUser() for adapter  */
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.getAllUser().observe(viewLifecycleOwner) {
            adapter.setListData(it)
        }
        //it là một biến ngầm định trong lambda expression, đại diện cho dữ liệu mới nhận được từ LiveData
        // ADD --> Hiện ADD Fragment ra
        binding.imgAddUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainManagerFragment_to_addUserFragment)
        }
    }

    private fun showEditDialog(itemUser: AccountEntity) {
        // 1.  Build Dialog
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        // 2.  Designed XML --> View
        val view = layoutInflater.inflate(R.layout.dialog_alert_edit_user, null)
        // 3.  Set VIEW tra ve above --> Dialog
        build.setView(view)

        // 4.  Code cho dau X
        view.findViewById<ImageView>(R.id.imgClose).setOnClickListener {
            dialog.dismiss()
        }

        // 5.  Handle Lock
        val lockUser = view.findViewById<LinearLayout>(R.id.llLockUser)
        lockUser?.setOnClickListener {
            requireContext().showToast("Lock this Account")
            dialog.dismiss()
        }

        // 6.  Handle Lock
        val resetUser = view.findViewById<LinearLayout>(R.id.llResetAccount)
        resetUser?.setOnClickListener {
            requireContext().showToast("Reset Account")
            dialog.dismiss()
        }




        // End. Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }

    override fun getInflateViewBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManagerUserBinding {
        return FragmentManagerUserBinding.inflate(layoutInflater, container, false)
    }


}