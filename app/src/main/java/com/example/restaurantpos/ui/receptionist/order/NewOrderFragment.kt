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
import com.example.restaurantpos.db.entity.CartEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.ui.manager.category.CategoryViewModel
import com.example.restaurantpos.util.showToast

class NewOrderFragment : Fragment() {

    lateinit var binding: FragmentNewOrderBinding
    lateinit var adapterCategoryInBottomOfOrderFragment: CategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterOrderItem: ItemOfCategoryInBottomOfOrderFragmentAdapter
    lateinit var adapterCartItem: CartItemAdapter

    /** Lấy những ViewModel chứa các phương thức cần sử dụng */
    lateinit var viewModelCategory: CategoryViewModel

    var data: TableEntity? = null
    var chooseCategory: Int = 1

    private var listItemOfCategory = ArrayList<ItemEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)

        /** Xử lý Biến data
        Chuyển TableEntity String thành 1 đối tượng để chuyển dữ liệu --> gán đối tượng này cho data*/
        data = TableEntity.toTableEntity(requireArguments().getString("data").toString())
        if (data == null) {
            findNavController().popBackStack()
        }

        /** Xử lý ViewModel */
        // ViewModelProvider: Lấy&quản lý ViewModels trong 1 LifecycleOwner như 1 Activity or 1 Fragment.
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Handle data Object: TableEntity above*/
        data?.let { table ->
            // Code cho tên Table
            binding.apply {
                txtTableNameInOrderList.text = table.table_name
            }
            // Khi có Data thì mới thực hiện getListCategory. Lấy listData từ DB đổ lên View qua Adapter
            getListCategory(chooseCategory)
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
                    // Phần Add này còn kiểm soát cả số lượng nữa --> Tạo ViewModel riêng.
                    context?.showToast("Add this Item to Cart")

                }

            }
        )
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rycOrderItem.adapter = adapterOrderItem

        /** Adapter 3 :CART ITEM:  Xử lý adapter, inflate for View*/
        // 1. Tạo 1 adapter
        adapterCartItem = CartItemAdapter(
            requireContext(),
            ArrayList(),
            viewLifecycleOwner,
            object : CartItemAdapter.EventClickCartItemListener {
                override fun clickMinus(orderedItem: CartEntity, pos: Int) {
                    TODO("Not yet implemented")
                }

                override fun clickPlus(orderedItem: CartEntity, pos: Int) {
                    TODO("Not yet implemented")
                }

                override fun clickNote(orderedItem: CartEntity, pos: Int) {
                    TODO("Not yet implemented")
                }

                override fun clickDelete(orderedItem: CartEntity, pos: Int) {
                    TODO("Not yet implemented")
                }


            }
        )
        // 2. Dùng adapter vừa tạo cho View cần dùng
        binding.rycOrderItem.adapter = adapterOrderItem

    }


    /** ----------------------------------------------------------*/

    // Set listData get được từ DB cho listData mà Adapter sử dụng, để đổ ra View.
    // Adapter:  CategoryInBottomOfOrderFragmentAdapter
    private fun getListCategory(chooseCategory: Int) {
        viewModelCategory.getAllCategory().observe(viewLifecycleOwner) {
            adapterCategoryInBottomOfOrderFragment.setListData(it)
//            getItemOfCategory(chooseCategory)

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