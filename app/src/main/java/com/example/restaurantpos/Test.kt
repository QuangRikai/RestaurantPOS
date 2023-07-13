//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.activity.OnBackPressedCallback
//import androidx.core.content.ContentProviderCompat.requireContext
//import com.example.restaurantpos.R
//import com.example.restaurantpos.db.entity.CartItemEntity
//import com.example.restaurantpos.db.entity.OrderEntity
//import com.example.restaurantpos.db.entity.TableEntity
//import com.example.restaurantpos.ui.staff.receptionist.checkout.ItemCheckoutAdapter
//import com.example.restaurantpos.util.DatabaseUtil
//import com.example.restaurantpos.util.DateFormatUtil
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//@SuppressLint("SetTextI18n")
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    val tax = 0.1f
//    var subTotal = 0.0f
//    var billAmount = 0.0f
//    var billAmountText = ""
//    var change = 0.0f
//
//    /** Adapter BILL */
//    // Luôn nhìn từ setListData ra.
//    // 1. Tạo 1 adapter
//    adapterItemCheckout = ItemCheckoutAdapter(requireContext(), ArrayList(), viewLifecycleOwner)
//    // 2. Dùng adapter vừa tạo cho View cần dùng
//    binding.rcyItemInBill.adapter = adapterItemCheckout
//
//    /** Xử lý đáp data từ fragment trước */
//    // Cần Table --> Chuyển Table về trạng thái Empty
//    // Cần Order --> Tính tiền cho Order đấy
//    tableObject =
//        TableEntity.toTableEntity(requireArguments().getString("tableObject").toString())
//    Log.d("Quanglt", "$tableObject")
//
//    orderObject =
//        OrderEntity.toOrderObject(requireArguments().getString("orderObject").toString())
//    Log.d("Quanglt", "$orderObject")
//
//
//    tableObject?.let { table ->
//        binding.txtTableName.text = table.table_name
//        viewModelCart.getListCartItemByTableIdAndOrderStatus(table.table_id)
//            .observe(viewLifecycleOwner) { listCart ->
//                adapterItemCheckout.setListData(listCart as ArrayList<CartItemEntity>)
//            }
//    }
//    /** ----------------------------------------------------------------------------------*/
//    /** Handle Checkout */
//    // Total = subTotal - subTotal*coupon + subTotal*Tax
//    // Change = Total - Cash
//    /** subTotal */
//    CoroutineScope(Dispatchers.IO).launch {
//        subTotal = DatabaseUtil.getSubTotal(orderObject!!.order_id)
//        binding.txtSubTotal.text = String.format("%.1f", subTotal)
//    }
//
//    /** ---------------------------------------------------------- */
//    /** 2. COUPON --> BILL AMOUNT */
//    fun calculateBillAmount() {
//        if (binding.edtCoupon.text.isNotEmpty()) {
//            val couponPercentage = binding.edtCoupon.text.toString().toFloat()
//            if (couponPercentage <= 100.0) {
//                billAmount = subTotal * (1 - couponPercentage / 100) * (1 + tax)
//            } else {
//                // Không thể nhập giá trị lớn hơn 100%
//            }
//        } else {
//            billAmount = subTotal * (1 + tax)
//        }
//        binding.txtBillAmount.text = String.format("%.1f", billAmount)
//    }
//
//    binding.txtAddCoupon.setOnClickListener {
//        if (binding.llCoupon.visibility == View.VISIBLE) {
//            binding.llCoupon.visibility = View.GONE
//            binding.txtAddCoupon.text = "Add Coupon"
//            calculateBillAmount()
//        } else {
//            binding.llCoupon.visibility = View.VISIBLE
//            binding.txtAddCoupon.text = "Hide Coupon"
//        }
//    }
//
//    binding.edtCoupon.doOnTextChanged { text, _, _, _ ->
//        calculateBillAmount()
//    }
//
//    /** 3. CASH --> CHANGE */
//    binding.edtCash.doOnTextChanged { text, _, _, _ ->
//        if (text.toString().isNotEmpty()) {
//            val cash = text.toString().toFloat()
//            if (cash > billAmount) {
//                change = cash - billAmount
//                binding.txtChange.text = String.format("%.1f", change) + " $"
//            } else {
//                binding.txtChange.text = "0.0"
//            }
//        } else {
//            binding.txtChange.text = "0.0"
//        }
//    }
//
//    /** ---------------------------------------------------------- */
//    /** Device's Back Button*/
//    val callback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            findNavController().popBackStack()
//        }
//    }
//    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//
//    /** Code for Back */
//    binding.imgBack.setOnClickListener {
//        findNavController().popBackStack()
//    }
//
//    /** Code for CHECK OUT */
//    binding.txtCheckout.setOnClickListener {
//        if (binding.edtCash.text.isNotEmpty()) {
//            val cash = binding.edtCash.text.toString().toFloat()
//            if (cash > billAmount) {
//                orderObject?.order_status_id = 2
//                orderObject?.payment_amount = cash
//                orderObject?.paid_time = DateFormatUtil.getTimeForOrderId()
//                orderObject?.let {
//                    viewModelCart.addOrder(it)
//                }
//
//                // Set lại Table is Empty and update Status on Database
//                tableObject?.table_status_id = 0
//                tableObject?.let { tableObject ->
//                    viewModelTable.addTable(requireContext(), tableObject)
//                }
//
//                findNavController().navigate(R.id.action_checkoutFragment_to_checkoutDoneFragment)
//            } else {
//                binding.txtError.show()
//            }
//        } else {
//            binding.txtError.show()
//        }
//    }
//}
