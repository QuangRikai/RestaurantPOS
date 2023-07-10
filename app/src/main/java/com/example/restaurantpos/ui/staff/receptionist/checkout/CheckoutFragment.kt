package com.example.restaurantpos.ui.staff.receptionist.checkout

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentCheckoutBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.manager.category.CategoryViewModel
import com.example.restaurantpos.ui.manager.customer.CustomerViewModel
import com.example.restaurantpos.ui.staff.receptionist.order.CartViewModel
import com.example.restaurantpos.ui.staff.receptionist.order.CustomerInnerAdapter
import com.example.restaurantpos.ui.staff.receptionist.table.TableViewModel
import com.example.restaurantpos.util.DateFormatUtil
import com.example.restaurantpos.util.gone
import com.example.restaurantpos.util.show
import com.example.restaurantpos.util.showToast
import java.util.Calendar


class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding

    /** ViewModel Object */
    private lateinit var viewModelCart: CartViewModel
    private lateinit var viewModelItem: CategoryViewModel
    private lateinit var viewModelTable: TableViewModel
    private lateinit var viewModelCustomer: CustomerViewModel

    private lateinit var adapterItemCheckout: ItemCheckoutAdapter
    private lateinit var adapterCustomerInner: CustomerInnerAdapter

    // Tạo sẵn Object --> Xíu nữa hứng data get được. Từ database/fragment before
    private var tableObject: TableEntity? = null
    private var orderObject: OrderEntity? = null
    private var customerObject: CustomerEntity? = null

    // Dialog cho Customer
    lateinit var dialog: AlertDialog

    val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        /** Create ViewModel Object */
        viewModelCart = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModelItem = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelTable = ViewModelProvider(this).get(TableViewModel::class.java)
        viewModelCustomer = ViewModelProvider(this).get(CustomerViewModel::class.java)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Xử lý đáp data từ fragment trước */
        // Cần Table --> Chuyển Table về trạng thái Empty
        // Cần Order --> Tính tiền cho Order đấy
        tableObject =
            TableEntity.toTableEntity(requireArguments().getString("tableObject").toString())
        Log.d("Quanglt", "$tableObject")

        orderObject =
            OrderEntity.toOrderObject(requireArguments().getString("orderObject").toString())
        Log.d("Quanglt", "$orderObject")

        /** Handle Checkout */
        val tax = 0.1f
        var subTotal = 0.0f
        var billAmount = 0.0f
        var change = 0.0f

        /** ----------------------------------------------------------------------------------*/
        binding.txtTax.text = "10 %"

        /** ----------------------------------------------------------------------------------*/
        tableObject?.let { table ->
            binding.txtTableName.text = table.table_name
            viewModelCart.getListCartItemByTableIdAndOrderStatus(table.table_id)
                .observe(viewLifecycleOwner) { listCart ->
                    adapterItemCheckout.setListData(listCart as ArrayList<CartItemEntity>)

                    /** 1. Sub Total */
                    for (i in 0 until listCart.size) {
                        val cartItem = listCart[i]
                        viewModelItem.getItemOfCategory(cartItem.item_id)
                            .observe(viewLifecycleOwner) { listItem ->
                                val item = listItem[0]
                                subTotal += (item.price * cartItem.order_quantity)

                                // Nếu là phần tử cuối cùng, cập nhật giá trị Sub Total
                                if (i == listCart.size - 1) {
                                    binding.txtSubTotal.text =
                                        String.format("%.1f", subTotal) + " $"
                                    binding.txtChange.text = "0.0 $"
                                }
                            }
                    }

                    /** 2. Bill Amount */
                    binding.edtCoupon.doOnTextChanged { text1, _, _, _ ->
                        if (text1 != null) {
                            billAmount = if (text1.isEmpty()) {
                                subTotal * (1 + tax)
                            } else {
                                (subTotal * (1 - text1.toString().toFloat() / 100)) * (1 + tax)
                            }
                            binding.txtBillAmount.text = String.format("%.1f", billAmount) + " $"
                        }
                    }

                    /** 3. Change */
                    binding.edtCash.doOnTextChanged { text, start, before, count ->
                        if (text != null) {
                            if (text.isEmpty() || (text.toString()
                                    .toFloat() < billAmount)
                            ) {
                                binding.txtChange.text = "0.0 $"
                            } else {
                                change = binding.edtCash.text.toString().toFloat() - billAmount
                                binding.txtChange.text = String.format("%.1f", change) + " $"
                            }
                        }
                    }
                }
        }
        /** ---------------------------------------------------------- */
        /** Device's Back Button*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        /** Code for Back */
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        /** Code for CHECK OUT */
        binding.txtDone.setOnClickListener {
            if (binding.txtChange.text != "Invalid") {
                orderObject?.order_status_id = 2
                orderObject?.payment_amount = binding.edtCash.text.toString().toFloat()
                orderObject?.paid_time = DateFormatUtil.getTimeForOrderId()
                orderObject?.let {
                    viewModelCart.addOrder(it)
                }

                // Set lại Table is Empty and update Status on Database
                tableObject?.table_status_id = 0
                tableObject?.let { tableObject ->
                    viewModelTable.addTable(requireContext(), tableObject)
                }

                // Xong thì trả về lại màn Table để order tiếp
                findNavController().navigate(R.id.action_checkoutFragment_to_checkoutDoneFragment)
            } else {
                context?.showToast("Customer has paid?")
            }
        }

        /** ----------------------------------------------------------------------------------*/
        /** Adapter BILL */
        // Luôn nhìn từ setListData ra.
        // 1. Tạo 1 adapter
        adapterItemCheckout = ItemCheckoutAdapter(requireContext(), ArrayList(), viewLifecycleOwner)
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rcyItemInBill.adapter = adapterItemCheckout

        /** Code for Customer TextView */
        binding.txtCustomerInBill.setOnClickListener {
            showDialogCustomer()
        }
    }

    /** ----------------------------------------------------------*/
    /** Add Customer Dialog */

    val startYear = calendar.get(Calendar.YEAR) - 20
    val startMonth = calendar.get(Calendar.MONTH) - 5
    val startDay = calendar.get(Calendar.DAY_OF_MONTH) - 10
    @SuppressLint("SetTextI18n")
    private fun showDialogCustomer() {
        // -----------------Prepare--------------------------------------------------//
        // 1.  Build Dialog
        // 2.  Designed XML --> View
        // 3.  Set VIEW tra ve above --> Dialog
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        val view = layoutInflater.inflate(R.layout.dialog_alert_add_customer, null)
        build.setView(view)
        // 4.  Get Component of Dialog
        val edtPhoneNumber = view.findViewById<EditText>(R.id.edtPhoneNumber)
        val rcyCustomerInPhone = view.findViewById<RecyclerView>(R.id.rcyCustomerInPhone)
        val edtCustomerName = view.findViewById<EditText>(R.id.edtCustomerName)
        val txtCustomerBirthday = view.findViewById<TextView>(R.id.txtCustomerBirthday)
        val btnAddCustomer = view.findViewById<Button>(R.id.btnAddCustomer)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val imgDate = view.findViewById<ImageView>(R.id.imgDate)
        val imgCloseDialogCustomer = view.findViewById<ImageView>(R.id.imgCloseDialogCustomer)
        // -----------------Code for Component----------------------------------------//
        // 1.  Handle Adapter CustomerPhone + Code of clickCustomerInner (Get CustomerInfo and set to View in Order)
        adapterCustomerInner = CustomerInnerAdapter(requireParentFragment(), ArrayList(), object :
            CustomerInnerAdapter.EventClickItemCustomerInnerListener {
            override fun clickCustomerInner(itemCustomer: CustomerEntity) {
                // Có sẵn thì pick-up ra thôi
                customerObject = itemCustomer

                /**???*/

                /**???*/
//                orderObject?.customer_id = itemCustomer.customer_id
                // Tìm cách đưa Customer's Name lên NewOrderFragment
                binding.txtCustomerInBill.text = itemCustomer.customer_name
                dialog.dismiss()
            }
        })
        rcyCustomerInPhone.adapter = adapterCustomerInner

        // 2. Code for when staff types on edtPhoneNumber and contain >= 3 Chars. If exist --> Show for Picking-up
        // SetData for (1)
        edtPhoneNumber.doOnTextChanged { text, start, before, count ->
            if (text.toString().length >= 3) {
                viewModelCustomer.getListCustomerByPhoneForSearch(text.toString())
                    .observe(viewLifecycleOwner) {
                        if (it.size > 0) {
                            adapterCustomerInner.setListData(it as ArrayList<CustomerEntity>)
                            rcyCustomerInPhone.show()
                        }
                    }
            } else {
                rcyCustomerInPhone.gone()
            }
        }

        // 3. Birthday
        imgDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    txtCustomerBirthday.text = "$year/${1 + month}/$dayOfMonth"
                },
                startYear, startMonth, startDay
            ).show()
        }

        // 4.  Add Customer
        btnAddCustomer.setOnClickListener {
            if (edtCustomerName.text.isEmpty() ||
                edtPhoneNumber.text.isEmpty() ||
                txtCustomerBirthday.text.isEmpty()
            ) {
                context?.showToast("Information must not be empty!")
            } else {
                viewModelCustomer.addCustomer(
                    CustomerEntity(
                        0,
                        edtCustomerName.text.toString(),
                        edtPhoneNumber.text.toString(),
                        txtCustomerBirthday.text.toString()
                    )
                )
            }


            viewModelCustomer.getListCustomerByPhoneForAdd(edtPhoneNumber.text.toString())
                .observe(viewLifecycleOwner) { listCustomer ->
                    if (listCustomer.size > 0) {
                        customerObject = listCustomer[0]
                        binding.txtCustomerInBill.text = listCustomer[0].customer_name
                        dialog.dismiss()
                    }
                }
        }


        // Other:  Dau X  &   Cancel Button
        imgCloseDialogCustomer.setOnClickListener { dialog.dismiss() }
        btnCancel.setOnClickListener { dialog.dismiss() }

        // End: Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }
}