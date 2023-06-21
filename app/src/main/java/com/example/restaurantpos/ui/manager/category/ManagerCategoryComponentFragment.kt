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
import java.math.RoundingMode

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
        val imgAddItem = binding.imgAddCategoryItem
        binding.imgAddCategoryItem.setOnClickListener {
            showAddCategoryItemDialog(imgAddItem)
        }

        viewModel.getListCategoryComponentItem(category.category_id).observe(viewLifecycleOwner) {
            adapter.setListData(it)
        }
    }

    private fun showAddCategoryItemDialog(imgAddItem: ImageView) {
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
//        view.findViewById<Button>(R.id.btnChoseImage).setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Photo, Quang"), 111)
//        }


        // 7.  Code cho AddItem Button
        view.findViewById<Button>(R.id.btnAddItem).setOnClickListener {
            viewModel.addCategoryItem(
                ItemEntity(
                    0,
                    dialog.findViewById<EditText>(R.id.edtItemName)?.text.toString(),
                    dialog.findViewById<EditText>(R.id.edtItemPrice)?.text.toString().toFloat().toBigDecimal().setScale(2, RoundingMode.HALF_UP).toFloat(),
//                    DataUtil.getStringFromList(listItemImage),
                    "Quang",
                    dialog.findViewById<EditText>(R.id.edtItemInventoryQuantity)?.text.toString()
                        .toInt(),
                    category.category_id,
                )
            )
            dialog.dismiss()
        }

        // End. Tao Dialog (Khi khai bao chua thuc hien) and Show len display
        dialog = build.create()
        dialog.show()
    }

    private var listItemImage = ArrayList<String>()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == AppCompatActivity.RESULT_OK ) {
            if (data != null && data.data != null) {
                try {
                    dialog.findViewById<ImageView>(R.id.imgShow)?.setImageBitmap(
                        MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            data.data   // URI cung cấp cho bên dưới
                        )
                    )
                    listItemImage.add(RealPathUtil.getRealPath(requireContext(), data.data))
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