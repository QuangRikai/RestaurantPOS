package com.example.restaurantpos.ui.staff.receptionist.order.oldOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentOldOrderBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.staff.receptionist.order.CartViewModel


class OldOrderFragment : Fragment() {

    lateinit var binding: FragmentOldOrderBinding

    lateinit var viewModelCart: CartViewModel

    lateinit var adapterCartItemInOldOrder: CartItemInOldOrderAdapter

    // Xử lý lấy table_id --> Lấy ra Order của Table đấy
    var tableObject: TableEntity? = null
    var orderObject: OrderEntity? = null
    var listCartItem = ArrayList<CartItemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOldOrderBinding.inflate(inflater, container, false)

        /** Tạo Đối Tượng ViewModel */
        // ViewModelProvider: Lấy&quản lý ViewModels trong 1 LifecycleOwner như 1 Activity or 1 Fragment.
        viewModelCart = ViewModelProvider(this).get(CartViewModel::class.java)
        /** Xử lý Biến tableObject (data từ fragment trước) */
        tableObject = TableEntity.toTableEntity(requireArguments().getString("data").toString())
        if (tableObject == null) {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Code for Back */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        /** Code for Add Img (Additional Order) */
        binding.txtAddOrderItem.setOnClickListener {
            findNavController().navigate(R.id.action_orderedTableFragment_to_addMoreOrderFragment2,
                bundleOf("tableObject" to tableObject?.toJson(), "orderObject" to orderObject?.toJson())
            )
        }


        /** Code for Checkout Img */
        // Tính tiền thì đáp sang bên kia OrderEntity thôi là okay rồi.
        // Hiển thị ra 1 cái list rồi tổng tiền.
        binding.txtCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_orderedTableFragment_to_checkoutFragment4,
                bundleOf("tableObject" to tableObject?.toJson(), "orderObject" to orderObject?.toJson())
            )
        }

        /** ----------------------------------------------------------------------------------*/
        /** Adapter CartItemInOldOrder:  Xử lý adapter, inflate for View*/
        // Luôn nhìn từ setListData ra.
        // 1. Tạo 1 adapter
        adapterCartItemInOldOrder = CartItemInOldOrderAdapter(
            requireContext(),
            viewLifecycleOwner,
            ArrayList(),
            object : CartItemInOldOrderAdapter.EventClickCartItemInOldOrderListener {
                override fun clickCartItemServedStatus(cartItemInOldOrder: CartItemEntity) {
                    val cartItem = cartItemInOldOrder
                    cartItem.cart_item_status++
                    viewModelCart.addCartItem(cartItem)
                }

            })
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rcyCartItemInOldOrder.adapter = adapterCartItemInOldOrder
        // 3. Set data cho adapder chuyển đổi.
        /**
        Get list cartItem theo bàn!!! --> Cần table_id
        Click Table --> Mang theo Info của Table vào ---> Cần bundleOf (data)
         */

        /** Handle data Object: tableEntity above*/
        tableObject?.let { table ->
            // Code cho tên Table
            binding.txtTableName.text = table.table_name
            //Get table_id for using
            viewModelCart.getOrderByTable(table.table_id).observe(viewLifecycleOwner) { order ->

                orderObject = order

                viewModelCart.getListCartItemByOrderId(order.order_id)
                    .observe(viewLifecycleOwner) { listCart ->
                        adapterCartItemInOldOrder.setListData(listCart)
                    }
            }

        }
        /** ----------------------------------------------------------------------------------*/


    }
}