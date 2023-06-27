package com.example.restaurantpos.ui.receptionist.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.restaurantpos.databinding.FragmentNewOrderBinding
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.manager.category.CategoryViewModel
import com.example.restaurantpos.ui.receptionist.table.TableViewModel
import com.example.restaurantpos.util.DateFormatUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.showToast

class NewOrderFragment : Fragment() {

    lateinit var binding: FragmentNewOrderBinding
    lateinit var adapterCategoryInBottomOfOrderFragment: CategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterOrderItem: ItemOfCategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterCartItem: CartItemAdapter

    /** Lấy những ViewModel chứa các phương thức cần sử dụng */
    lateinit var viewModelCategory: CategoryViewModel
    lateinit var viewModelCart: CartViewModel
    lateinit var viewModelTable: TableViewModel


    /** Tạo những đối tượng của Bảng để dễ thao tác */
    var tableObject: TableEntity? = null
    var orderObject: OrderEntity? = null
    var listCartItem = ArrayList<CartItemEntity>()


    var chooseCategory: Int = 1

    private var listItemOfCategory = ArrayList<ItemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        /** ----------------------------------------------------------------------------*/
        /** Xử lý Biến data*/
        // Chuyển TableEntity String thành 1 đối tượng để chuyển dữ liệu --> gán đối tượng này cho data
        tableObject = TableEntity.toTableEntity(requireArguments().getString("data").toString())
        if (tableObject == null) {
            findNavController().popBackStack()
        }

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
        tableObject?.let { table ->
            // Code cho tên Table
            binding.apply {
                txtTableNameInOrderList.text = table.table_name
            }
            // Khi có Data thì mới thực hiện getListCategory. Lấy listData từ DB đổ lên View MenuOrder
            getListCategory(chooseCategory)

            // Khi có Table (Đã click chọn Table) mới bắt đầu tạo Order
            orderObject = OrderEntity(
                DateFormatUtil.getTimeForOrderId(),
                0,
                table.table_id,
                SharedPreferencesUtils.getAccountId(),
                DateFormatUtil.getTimeForOrderId(),
                "",
                0f,
                0
            )

// Vừa vào NewOrder là đã phải thay đổi trạng thái của bàn rồi. Tránh việc 2 bàn cùng order 1 lúc
            table.table_status = 1
            // Thêm bàn vào mà làm gì?
            viewModelTable.addTable(requireContext(), table)

        }

        /** Code for Back */
        binding.igmBackOfOrder.setOnClickListener {
            // Nếu là bàn Order thêm (status = 2) thì không làm gì
            if (tableObject!!.table_status == 1) {
                tableObject!!.table_status = 0
            }

            viewModelTable.addTable(requireContext(), tableObject!!)
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
            tableObject?.table_status = 1
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
                    listCartItem.add(
                        CartItemEntity(
                            0,
                            itemInCategory.item_id,
                            orderObject!!.order_id,
                            1,
                            "",
                            0
                        )
                    )

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
            ArrayList(),
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
                    // Xử lý Dialog
                    context?.showToast("Xử lý Dialog")

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
