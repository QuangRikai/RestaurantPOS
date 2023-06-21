package com.example.restaurantpos.ui.receptionist.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentTableBinding
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.login.LoginActivity
import com.example.restaurantpos.ui.manager.user.UserViewModel
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity

class TableFragment : Fragment(), TableAdapter.EventClickTableListener{

    lateinit var binding: FragmentTableBinding
    lateinit var adapter: TableAdapter
    private lateinit var viewModel: TableViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** ViewModel --> Handle Database */
        viewModel = ViewModelProvider(this).get(TableViewModel::class.java)
        viewModel.addTable(requireContext(),TableEntity(1, "Table 01"))
        viewModel.addTable(requireContext(),TableEntity(2, "Table 02"))
        viewModel.addTable(requireContext(),TableEntity(3, "Table 03"))
        viewModel.addTable(requireContext(),TableEntity(4, "Table 04"))
        viewModel.addTable(requireContext(),TableEntity(5, "Table 05"))
        viewModel.addTable(requireContext(),TableEntity(6, "Table 06"))
        viewModel.addTable(requireContext(),TableEntity(7, "Table 07"))
        viewModel.addTable(requireContext(),TableEntity(8, "Table 08"))
        viewModel.addTable(requireContext(),TableEntity(9, "Table 09"))

        viewModel.getAllTable().observe(viewLifecycleOwner) {
            adapter.setListData(it)
        }


        /** Adapter */
        adapter = TableAdapter(requireContext(), mutableListOf(), this)
        binding.rcyTable.adapter = adapter

        /** ToolBar */
        // 1. Get Account's Name
        binding.txtLoginAccountName.text = SharedPreferencesUtils.getAccountName()
        // 2. Logout Button
        binding.imgMenuToolBar.setOnClickListener { it ->
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.inflate(R.menu.popup_menu_main_manager)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_logout -> {
                        requireContext().openActivity(LoginActivity::class.java, true)
                        true
                    }
                    else -> true
                }
            }

            try {
                val popup = popupMenu::class.java.getDeclaredField("qPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
        }
    }

    override fun clickTable(itemUser: TableEntity) {


    }
}
