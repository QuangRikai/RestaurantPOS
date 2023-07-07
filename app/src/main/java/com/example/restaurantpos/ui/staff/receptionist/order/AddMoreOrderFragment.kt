package com.example.restaurantpos.ui.staff.receptionist.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentAddMoreOrderBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.manager.category.CategoryViewModel
import com.example.restaurantpos.ui.staff.receptionist.order.newOrder.CartItemAdapter
import com.example.restaurantpos.ui.staff.receptionist.order.newOrder.CategoryInBottomOfOrderFragmentAdapter
import com.example.restaurantpos.ui.staff.receptionist.order.newOrder.ItemOfCategoryInBottomOfOrderFragmentAdapter
import com.example.restaurantpos.ui.staff.receptionist.order.newOrder.NewOrderFragment
import com.example.restaurantpos.ui.staff.receptionist.table.TableViewModel
import com.example.restaurantpos.util.showToast


class AddMoreOrderFragment : Fragment() {

    lateinit var binding: FragmentAddMoreOrderBinding
    lateinit var adapterCategoryInBottomOfOrderFragment: CategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterOrderItem: ItemOfCategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterCartItem: CartItemAdapter

    /** Lấy những ViewModel chứa các phương thức cần sử dụng */
    lateinit var viewModelCategory: CategoryViewModel
    lateinit var viewModelCart: CartViewModel
    lateinit var viewModelTable: TableViewModel


    /** Tạo những đối tượng của Bảng để dễ thao tác */
    private var tableObject: TableEntity? = null
    private var orderObject: OrderEntity? = null
    private var listItemOfCategory = ArrayList<ItemEntity>()
    private var listCartItem = ArrayList<CartItemEntity>()

    // Thực hiện xóa ở đây thì hãy cẩn thận.
    // Cần lưu nó lại vì thao tác trên này không lưu vào Database nhưng khi mà xóa thì phải lưu nó vào lại cả dataBase nữa
    // 1:46
    // Khi Click Order thì cần Dọn sạch (Delete), không còn liên quan gì nữa
    private var listCartItemDelete = ArrayList<CartItemEntity>()

    // Để so sánh với category_id
    var chooseCategory: Int = 1

    // Dialog cho add Item's Note
    lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMoreOrderBinding.inflate(inflater, container, false)
        /** ----------------------------------------------------------------------------*/
        /** Đáp Data từ Fragment trước sang */
        tableObject =
            TableEntity.toTableEntity(requireArguments().getString("tableObject").toString())
        if (tableObject == null) {
            findNavController().popBackStack()
        }

        orderObject =
            OrderEntity.toOrderObject(requireArguments().getString("orderObject").toString())
        if (orderObject == null) {
            findNavController().popBackStack()
        }

        // Thằng này cần truyền sang 1 List<String>  --> Căng quá. Thay vì get từ Bếp, thì get từ order luôn.
        /*        listCartItem =
                    Gson().fromJson(requireArguments().getString("listCartItem").toString(), ArrayList<CartItemEntity>)
                if (listCartItem == null) {
                    findNavController().popBackStack()
                }*/

        /** Tạo Đối Tượng ViewModel */
        // ViewModelProvider: Lấy&quản lý ViewModels trong 1 LifecycleOwner như 1 Activity or 1 Fragment.
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelCart = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModelTable = ViewModelProvider(this).get(TableViewModel::class.java)

        /** ----------------------------------------------------------------------------*/
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Handle data Object: tableEntity above*/
        // 1. tableObject
        tableObject?.let { table ->
            /** Code for Table's Name */
            binding.txtTableNameInOrderList.text = table.table_name

            // Xử lý cho Category View
            getListCategory(chooseCategory)

//            table.table_status = 1
//            viewModelTable.addTable(requireContext(), table)
        }

        // 2. orderObject --> Lấy listCartItem from Order. Dùng ... Lọc ra những thằng của Table AND status Waiting.
        orderObject?.let { order ->
            viewModelCart.getListCartItemOnWaiting(order.order_id)
                .observe(viewLifecycleOwner) { listItem ->
//                listCartItem.clear()
                    listCartItem.addAll(listItem)
                    adapterCartItem.setListData(listCartItem)
                }
        }

        /** Code for Back */
        binding.igmBackOfOrder.setOnClickListener {
            findNavController().popBackStack()
        }

        /** Code for Search */
        //Lọc listOrderItem theo Text và update lại listData cho adapter!
        binding.edtSearchOrderItem.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 1) {
                searchItem(text.toString())
            } else {
                adapterOrderItem.setListData(listItemOfCategory)
            }
        }
        /** Code for Clear Button */
        binding.txtClear.setOnClickListener {
            // Không dùng listCartItem.clear(). Tránh Crash
            listCartItem = ArrayList()
            adapterCartItem.setListData(listCartItem)
        }

        /** Code for Order Button */
        binding.txtOrder.setOnClickListener {
            // Add Order (Bill) vào OrderEntity
            orderObject?.let { order -> viewModelCart.addOrder(order) }
            // Add list Item_in_Cart into CartItemEntity. Lúc này mới viết vào Database!
            viewModelCart.addListCartItem(listCartItem)
            // Cập nhập trạng thái cho Table
            // Chú ý: 2 thằng không thể order cùng lúc cùng 1 cái bàn được
            /** ------------------------------????????-------------------------*/
            tableObject?.table_status_id = 2
            viewModelTable.addTable(requireContext(), tableObject!!)

            findNavController().popBackStack()
        }


        /** -------------------------------ADAPTER-------------------------*/
        /** Adapter 1: CATEGORY in BOTTOM:  Xử lý adapter, inflate for View*/
        // 1. Tạo 1 adapter
        adapterCategoryInBottomOfOrderFragment = CategoryInBottomOfOrderFragmentAdapter(
            requireContext(),
            ArrayList(),
            chooseCategory,
            viewLifecycleOwner,

            /** Phần này chưa rõ!*/
            object : CategoryInBottomOfOrderFragmentAdapter.EventClickCategoryInOrderListener {
                override fun clickCategoryInOrder(chooseCategory: Int) {
                    NewOrderFragment().chooseCategory = chooseCategory
                    adapterCategoryInBottomOfOrderFragment.setChooseCategory(chooseCategory)

                    // Get listItem của thằng được chọn --> Thực hiện các thao tác ở dưới hàm.
                    getItemOfCategory(chooseCategory)
                }
            }
        )
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rycCategoryInOrder.adapter = adapterCategoryInBottomOfOrderFragment

        /** Adapter 2:ORDER ITEM:  Xử lý adapter, inflate for View*/
        // 1. Tạo 1 adapter
        adapterOrderItem = ItemOfCategoryInBottomOfOrderFragmentAdapter(
            requireContext(),
            ArrayList(),
            object : ItemOfCategoryInBottomOfOrderFragmentAdapter.EventClickOrderItemListener {
                override fun clickAddOrderItem(itemInCategory: ItemEntity) {
                    // Xử lý Nhấp Add tiếp thì sẽ tăng Order_Quantity ở Cart
                    for (i in 0 until listCartItem.size) {
                        if (listCartItem[i].item_id == itemInCategory.item_id) {
                            listCartItem[i].order_quantity++
                            adapterCartItem.setListData(listCartItem)
                            return
                        }
                    }

                    // Đưa OrderItem --> Cart
/*                    listCartItem.add(
                        CartItemEntity(
                            0,
                            itemInCategory.item_id,
                            orderObject!!.order_id,
                            1,
                            "",
                            0
                        )
                    )*/

                    // Set Data (listCartItem) cho adapter --> Đổ lên View CartOrder
                    adapterCartItem.setListData(listCartItem)
                }

            }
        )
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rycOrderItem.adapter = adapterOrderItem

        /** Adapter 3 :CART ITEM:  Xử lý adapter, inflate for View*/
        // 1. Tạo 1 adapter
        adapterCartItem = CartItemAdapter(
            requireParentFragment(),
            ArrayList(), // Đáp sẵn bên kia sang nên không cần tạo rỗng set lại các thứ.
            viewLifecycleOwner,
            object : CartItemAdapter.EventClickCartItemListener {
                override fun clickMinus(orderedItem: CartItemEntity, pos: Int) {
                    if (listCartItem[pos].order_quantity == 1) {
                        listCartItem.remove(orderedItem)
                    } else {
                        listCartItem[pos].order_quantity--
                    }
                    adapterCartItem.setListData(listCartItem)
                }

                override fun clickPlus(orderedItem: CartItemEntity, pos: Int) {
                    listCartItem[pos].order_quantity++
                    adapterCartItem.setListData(listCartItem)
                }

                override fun clickNote(orderedItem: CartItemEntity, pos: Int) {
                    showDialogNote(orderedItem, pos)
                    adapterCartItem.setListData(listCartItem)

                }

                override fun clickDelete(orderedItem: CartItemEntity, pos: Int) {
                    listCartItem.remove(orderedItem)
                    adapterCartItem.setListData(listCartItem)
                }
            }
        )
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rycCartItemList.adapter = adapterCartItem

    }


    /** ----------------------------------------------------------*/

    // Set listData get được từ DB cho listData mà Adapter sử dụng, để đổ ra View.
    // Adapter:  CategoryInBottomOfOrderFragmentAdapter
    private fun getListCategory(chooseCategory: Int) {
        viewModelCategory.getAllCategory().observe(viewLifecycleOwner) {
            adapterCategoryInBottomOfOrderFragment.setListData(it)
            getItemOfCategory(chooseCategory)
            // Không có cái này thì sẽ không hiện gì ở MenuOrder!

        }
    }

    // Set listData get được từ DB cho listData mà Adapter sử dụng, để đổ ra View.
    // Adapter:  ItemOfCategoryInBottomOfOrderFragmentAdapter
    private fun getItemOfCategory(category_id: Int) {
        viewModelCategory.getListCategoryComponentItem(category_id)
            .observe(viewLifecycleOwner) { listItem ->
                adapterOrderItem.setListData(listItem)
                // Khi get được ra thì đổ vô listItemOfCategory --> Xử lý Filter!
                listItemOfCategory.clear()
                listItemOfCategory.addAll(listItem)
            }
    }


    private fun searchItem(searchString: String) {
        val filterList = ArrayList<ItemEntity>()
        listItemOfCategory.forEach { item ->
            if (item.item_name.contains(searchString)) {
                filterList.add(item)
            }
        }
        // Adapter: Bây giờ source of Data là filterList
        adapterOrderItem.setListData(filterList)
    }

    /** Note Dialog */
    private fun showDialogNote(orderedItem: CartItemEntity, pos: Int) {
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        val view = layoutInflater.inflate(R.layout.dialog_alert_add_note, null)
        build.setView(view)
        // ------------------------------------------------------------------------
        val edtNote = view.findViewById<EditText>(R.id.edtNote)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        // ------------------------------------------------------------------------
        // 4.  Add Button: Thêm note và hiện note lên cartItem đã note
        btnAdd.setOnClickListener {
            listCartItem[pos].note = edtNote.text.toString()
            adapterCartItem.setListData(listCartItem)
            dialog.dismiss()
        }


        // Other:  Dau X  &   Cancel Button
        imgClose.setOnClickListener { dialog.dismiss() }
        btnCancel.setOnClickListener { dialog.dismiss() }

        // End: Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()

    }

}
/** Quy trình sử dụng adapter (Ôn tập) */
/*
1. Khai báo adapter kiểu Adapter vừa tạo
2. Gán giá trị cho Adapter: Truyền vào những tham số cần thiết để tạo ra 1 Adapter Object
   Đoạn này cũng ảo diệu lắm nha!
2+. (Có thể nằm trong bước 2 hoặc cho Fragment kế thừa rồi tách hàm riêng ra)
   Thực hiện phương thức đã cài vào trong Interface

3. Thực hiện chuyển đổi:
   binding.rcy.adater = adapterInStep2
4. Sử dụng ViewModel để đưa listData (Category) get được, lên View
 */

/** Quy trình sử dụng ViewModel (Ôn tập) */
/*
1. Khai báo biến viewModel kiểu ViewModel vừa tạo/đã có
2. Tạo 1 đối tượng ViewModel, để tương tác với Fragment này.
3. Thực hiện các event (Các Event này xem ở Item_Category_In_Order
   - Category Được chọn thì: Đổi màu cho CategoryName, getListItem của Category đó ra.
4. Sử dụng ViewModel để đưa listData (Category) get được, lên View
 */

/** Xử lý Order */
/*
1. Tạo Order sau khi đã có Table, gắn vào biến orderEntity
2. Tạo listCartItem = ArrayList<CartItemEntity>() --> Add CartItem --> Adapter đổ lên cho View CartItem
3. Tạo CartViewModel để xử lý Cart and Order
4.
 */
/** Xử lý Order */
/*
1. Những thứ không liên quan đến Adapter thì quăn lên trên xử lý cho dễ nhìn

 */
