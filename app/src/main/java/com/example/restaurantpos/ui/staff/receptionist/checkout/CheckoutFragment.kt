package com.example.restaurantpos.ui.staff.receptionist.checkout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentCheckoutBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.staff.receptionist.order.CartViewModel
import com.example.restaurantpos.ui.staff.receptionist.table.TableViewModel


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBinding

    /** ViewModel Object */
    lateinit var viewModelCart: CartViewModel
    lateinit var viewModelTable: TableViewModel

    lateinit var adapterItemCheckout: ItemCheckoutAdapter

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
        tableObject =
            TableEntity.toTableEntity(requireArguments().getString("tableObject").toString())
        Log.d("Quanglt", "tableObject")
        orderObject =
            OrderEntity.toOrderObject(requireArguments().getString("orderObject").toString())
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
            // Set lại Table is Empty and update Status on Database
            tableObject?.table_status_id = 0
            tableObject?.let { tableObject ->
                viewModelTable.addTable(
                    requireContext(),
                    tableObject
                )
            }

            // Set Bill's Status is "Đã Thanh Toán" and update Status on Database
            orderObject?.order_status_id = 2
            orderObject?.let { orderObject -> viewModelCart.addOrder(orderObject) }

            // Xong thì trả về lại màn Table để order tiếp
            findNavController().navigate(R.id.action_checkoutFragment_to_tableFragment2)
        }

        /** ----------------------------------------------------------------------------------*/
        /** Adapter BILL */
        // Luôn nhìn từ setListData ra.
        // 1. Tạo 1 adapter
        adapterItemCheckout = ItemCheckoutAdapter(requireContext(), ArrayList(), viewLifecycleOwner)
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rcyItemInBill.adapter = adapterItemCheckout
        // 3. Set data cho adapder chuyển đổi.
        /** Idea: Lấy ra tất cả cartItem của bàn*/
        // Okay đã lấy được nhưng nó lấy hết từ trước tới nay luôn ==> Tèo.
        tableObject?.let { table ->
            // Code cho tên Table
            binding.txtTableName.text = table.table_name
            //Get table_id for using
            viewModelCart.getOrderByTable(table.table_id).observe(viewLifecycleOwner) { order ->
                orderObject = order
                viewModelCart.getListCartItemByTableId(order.table_id).observe(viewLifecycleOwner){listCart ->
                    adapterItemCheckout.setListData(listCart as ArrayList<CartItemEntity>)
                }
            }

        }
        /** ----------------------------------------------------------------------------------*/


    }
}