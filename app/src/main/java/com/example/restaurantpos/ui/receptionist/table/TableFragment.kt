package com.example.restaurantpos.ui.receptionist.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentTableBinding
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.login.LoginActivity
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
    /** clickTable --> Navigate to Order/Ordered Fragment */
    /** bundleOf(26 and 55) */
    override fun clickTable(itemTable: TableEntity, table_status: Int) {
        if(table_status == 0){
            findNavController().navigate(R.id.action_tableFragment_to_orderFragment,
                bundleOf("data" to itemTable.toJson()))
        }
        if(table_status == 2){
            findNavController().navigate(R.id.orderedTableFragment,
                bundleOf("data" to itemTable.toJson()))
        }
    }
}
