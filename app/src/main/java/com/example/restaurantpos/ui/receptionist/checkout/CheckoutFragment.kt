package com.example.restaurantpos.ui.receptionist.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentCheckoutBinding
import com.example.restaurantpos.databinding.FragmentOldOrderBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.receptionist.order.CartViewModel
import com.example.restaurantpos.ui.receptionist.order.oldOrder.CartItemInOldOrderAdapter
import com.example.restaurantpos.ui.receptionist.table.TableViewModel


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBinding
    /** ViewModel Object */
    lateinit var viewModelCart: CartViewModel
    lateinit var viewModelTable : TableViewModel

//    lateinit var adapterItemCheckout: ItemCheckoutAdapter

    // Tạo sẵn Object --> Xíu nữa hứng data get được. Từ database/fragment before
    var tableObject: TableEntity? = null
    var orderObject: OrderEntity? = null
    var listCartItem = ArrayList<CartItemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        /** Create ViewModel Object */
        viewModelCart = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModelTable = ViewModelProvider(this).get(TableViewModel::class.java)

        /** Xử lý đáp data từ fragment trước */
        // Cần Table --> Chuyển Table về trạng thái Empty
        // Cần Order --> Tính tiền cho Order đấy
        tableObject = TableEntity.toTableEntity(requireArguments().getString("tableObject").toString())
        Log.d("Quanglt","tableObject")
        orderObject = OrderEntity.toOrderObject(requireArguments().getString("orderObject").toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Code for Back */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        /** Code for DONE */
        binding.txtDone.setOnClickListener {
            tableObject?.table_status = 0
            tableObject?.let { tableObject-> viewModelTable.addTable(requireContext(), tableObject)}

            orderObject?.order_status = 2
            orderObject?.let { orderObject-> viewModelCart.addOrder(orderObject)}

            findNavController().navigate(R.id.action_checkoutFragment_to_tableFragment2)
        }

        /** Code for Add Img (Additional Order) */
//        binding.txtAddOrderItem.setOnClickListener {
//            findNavController().navigate(R.id.action_orderedTableFragment_to_addMoreOrderFragment2,
//                bundleOf("tableObject" to tableObject?.toJson(), "orderObject" to orderObject?.toJson())
//            )
//        }


        /** Code for Checkout Img */
        // Tính tiền thì đáp sang bên kia OrderEntity thôi là okay rồi.
        // Hiển thị ra 1 cái list rồi tổng tiền.
//        binding.txtCheckout.setOnClickListener {
//            findNavController().navigate(R.id.action_orderedTableFragment_to_addMoreOrderFragment2,
//                bundleOf( "orderObject" to orderObject?.toJson())
//            )
//        }
//
        /** ----------------------------------------------------------------------------------*/
//        /** Adapter BILL --> Tự xử nha! */
//        // Luôn nhìn từ setListData ra.
//        // 1. Tạo 1 adapter
//        adapterCartItemInOldOrder = CartItemInOldOrderAdapter(
//            requireContext(),
//            viewLifecycleOwner,
//            ArrayList(),
//            object : CartItemInOldOrderAdapter.EventClickCartItemInOldOrderListener {
//                override fun clickCartItemServedStatus(cartItemInOldOrder: CartItemEntity) {
//                    val cartItem = cartItemInOldOrder
//                    cartItem.cart_item_status++
//                    viewModelCart.addCartItem(cartItem)
//                }
//
//            })
//        // 2. Dùng adapter vừa tạo cho View cần dùng
//        binding.rcyCartItemInOldOrder.adapter = adapterCartItemInOldOrder
//        // 3. Set data cho adapder chuyển đổi.
//        /**
//        Get list cartItem theo bàn!!! --> Cần table_id
//        Click Table --> Mang theo Info của Table vào ---> Cần bundleOf (data)
//         */
//
        /** Handle data Object: tableEntity above*/
//        tableObject?.let { table ->
//            // Code cho tên Table
//            binding.txtTableName.text = table.table_name
//            //Get table_id for using
//            viewModelCart.getOrderByTable(table.table_id).observe(viewLifecycleOwner) { order ->
//
//                orderObject = order
//
//                viewModelCart.getListCartItemByOrder(order.order_id)
//                    .observe(viewLifecycleOwner) { listCart ->
//                        adapterCartItemInOldOrder.setListData(listCart)
//                    }
//            }
//
//        }
        /** ----------------------------------------------------------------------------------*/


    }
}