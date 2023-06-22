package com.example.restaurantpos.ui.manager.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.FragmentManagerCategoryComponentBinding
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.util.RealPathUtil
import com.example.restaurantpos.util.showToast
import java.io.IOException

/**
 * Truyền vào position: Int để chuyển tab
 * Truyền vào category: CategoryEntity để get ra CategoryComponent
 */
class ManagerCategoryComponentFragment(position: Int, var category: CategoryEntity) : Fragment(),
    ManagerCategoryComponentAdapter.EventClickItemCategoryListener {

    private lateinit var binding: FragmentManagerCategoryComponentBinding
    private lateinit var adapter: ManagerCategoryComponentAdapter
    private lateinit var viewModel: CategoryViewModel
    lateinit var dialog: AlertDialog
    private var itemImagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerCategoryComponentBinding.inflate(inflater, container, false)

        adapter = ManagerCategoryComponentAdapter(requireContext(), mutableListOf(), this)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        binding.rcyCategoryCoponentManagement.adapter = adapter

        return binding.root
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add Item Data vao Database
        /*        viewModel.addCategoryItem(
                    ItemEntity(
                        0,
                        "Mon Nhau 1",
                        11.1f,
                        "", // Tạo chuỗi để chốc nữa có được số lượng
                        5,
                        category.category_id
                    )
                )*/

        // Chuc nang Add Category Item
//        val imgAddItem = binding.imgAddCategoryItem
        binding.imgAddCategoryItem.setOnClickListener {
            showAddCategoryItemDialog()
        }

        viewModel.getListCategoryComponentItem(category.category_id).observe(viewLifecycleOwner) {
            adapter.setListData(it)
        }
    }

    private fun showAddCategoryItemDialog() {
        itemImagePath = ""
        // 1.  Build Dialog
        val build = AlertDialog.Builder(requireActivity(), R.style.ThemeCustom)
        // 2.  Designed XML --> View
        val view = layoutInflater.inflate(R.layout.dialog_alert_add_category_item, null)
        // 3.  Set VIEW tra ve above --> Dialog
        build.setView(view)

        // 4.  Code cho dau X
        view.findViewById<ImageView>(R.id.imgCloseDialogAddItem).setOnClickListener {
            dialog.dismiss()
        }

        // 5.  Code cho Cancel Button
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        // 6.  Code cho ADD Button
        /** Hàm này chưa hiểu rõ */
        view.findViewById<Button>(R.id.btnChoseImage).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Photo Quang"), 101)
        }


        // 7.  Code cho AddItem Button
        view.findViewById<Button>(R.id.btnAddItem).setOnClickListener {
            viewModel.addCategoryItem(
                ItemEntity(
                    0,
                    dialog.findViewById<EditText>(R.id.edtItemName)?.text.toString(),
                    dialog.findViewById<EditText>(R.id.edtItemPrice)?.text.toString().toFloat(),
//                    DataUtil.getStringFromList(listItemImage),
                    itemImagePath,
                    dialog.findViewById<EditText>(R.id.edtItemInventoryQuantity)?.text.toString()
                        .toInt(),
                    category.category_id
                )
            )
            dialog.dismiss()
        }

        // End. Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }


    /** Hàm này chưa hiểu rõ */

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, dataIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataIntent)

        if (requestCode == 101 && resultCode == AppCompatActivity.RESULT_OK) {
            if ((dataIntent != null) && (dataIntent.data != null)) {
                try {
                    dialog.findViewById<ImageView>(R.id.imgShow)?.setImageBitmap(
                        MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            dataIntent.data   // URI cung cấp cho bên dưới
                        )
                    )
                    itemImagePath = RealPathUtil.getRealPath(requireContext(), dataIntent.data)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                requireContext().showToast("Có Lỗi")
            }

        }
    }

    override fun longClickCategoryItem(itemCategory: ItemEntity) {
        // Change Item
    }
}