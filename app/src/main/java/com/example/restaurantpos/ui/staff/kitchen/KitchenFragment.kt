package com.example.restaurantpos.ui.staff.kitchen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentKitchenBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.ui.login.LoginActivity
import com.example.restaurantpos.ui.staff.receptionist.order.CartViewModel
import com.example.restaurantpos.util.DatabaseUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity
import com.example.restaurantpos.util.showToast

// Nhận định: Thằng này sử dụng lại ViewModel của CartViewModel
class KitchenFragment : Fragment() {

    lateinit var binding: FragmentKitchenBinding

    lateinit var viewModelCart: CartViewModel

    lateinit var adapterCartItemInKitchen: CartItemInKitchenAdapter
    lateinit var dialog: AlertDialog

    /*
    Sort theo Order_id (Order_create_id)
    sortByTimeOfOrder = 0 --> Không Sort/Giữ nguyên tăng dần      Ascending
    sortByTimeOfOrder = 1 --> Sort ngược (Giảm dần)               Descending
    sortByTimeOfOrder = 2 --> Bỏ qua

    Chuyển sortByTimeOfOrder sang MutableLiveData --> Lắng nghe
    */
    private var sortByTimeOfOrder = MutableLiveData(0)

//    var listCartItem = ArrayList<CartItemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKitchenBinding.inflate(inflater, container, false)

        /** Tạo Đối Tượng ViewModel */
        // ViewModelProvider: Lấy&quản lý ViewModels trong 1 LifecycleOwner như 1 Activity or 1 Fragment.
        viewModelCart = ViewModelProvider(this).get(CartViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        context?.openActivity(LoginActivity::class.java, true)
                        true
                    }

                    R.id.menu_update_info -> {
                        findNavController().navigate(R.id.action_kitchenFragment_to_updateStaffInfoFragment)
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

        /** Kitchen Shift Show */
        binding.txtShift.setOnClickListener {
            findNavController().navigate(
                R.id.action_kitchenFragment_to_shiftOfStaffFragment,
                bundleOf("shiftOfStaff" to 2)
            )
        }
        /** ----------------------------------------------------------------------------------*/

        /** Adapter CartItemInKitchen:  Xử lý adapter, inflate for View*/
        // Luôn nhìn từ setListData ra
        // 1. Tạo 1 adapter
        adapterCartItemInKitchen = CartItemInKitchenAdapter(
            requireContext(),
            viewLifecycleOwner,
            ArrayList(),
            object : CartItemInKitchenAdapter.EventClickCartItemInKitchenListener {
                override fun clickCartItemStatus(cartItemInKitchen: CartItemEntity) {
                    if (cartItemInKitchen.cart_item_status_id == 1) {
                        showConfirmItemStatusDialog(cartItemInKitchen)
                    } else {
                        cartItemInKitchen.cart_item_status_id++
                        viewModelCart.addCartItem(cartItemInKitchen)
                    }
                }
            })
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rcyCartItemInKitchen.adapter = adapterCartItemInKitchen
        // 3. Set data cho adapder chuyển đổi.
        // Thêm quả Sort!. Sort bao nhiêu thằng thì truyền vào bấy nhiêu thằng
        // sortByTimeOfOrder sang MutableLiveData --> Lắng nghe --> Thay đổi thì cập nhật lại listData
        // sortValue--> sortByTimeOfOrder.value!!  <--  Do nó cập nhật lại thằng nó lại ở trạng thái không có gì nên nó không load lại nữa.
        // 59:15 !!!

        sortByTimeOfOrder.observe(viewLifecycleOwner) { sortValue ->
            viewModelCart.getListCartItemOfKitchenBySortTime(sortByTimeOfOrder.value!!)
                .observe(viewLifecycleOwner) { listCart ->
                    adapterCartItemInKitchen.setListData(listCart as ArrayList<CartItemEntity>)
                }
        }

        when (sortByTimeOfOrder.value) {
            1 -> sortByTimeOfOrder.value = 0
            0 -> sortByTimeOfOrder.value = 1
        }


    }

    private fun showConfirmItemStatusDialog(cartItemInKitchen: CartItemEntity) {
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        val view = layoutInflater.inflate(R.layout.dialog_alert_confirm_status_in_kitchen, null)
        build.setView(view)


        val btnDone = view.findViewById<Button>(R.id.btnDone)
        val btnRevert = view.findViewById<Button>(R.id.btnRevert)
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)


        imgClose.setOnClickListener { dialog.dismiss() }


        btnDone.setOnClickListener {
            DatabaseUtil.getItemOfCategory(cartItemInKitchen.item_id)
                .observe(viewLifecycleOwner) {
                    context?.showToast("Done ${it[0].item_name}. Send to Receptionist")
                }
            cartItemInKitchen.cart_item_status_id++
            viewModelCart.addCartItem(cartItemInKitchen)
            viewModelCart.addCartItem(cartItemInKitchen)
            dialog.dismiss()
        }

        btnRevert.setOnClickListener {
            cartItemInKitchen.cart_item_status_id = 1
            viewModelCart.addCartItem(cartItemInKitchen)
            dialog.dismiss()
        }
        // End. Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }
}
