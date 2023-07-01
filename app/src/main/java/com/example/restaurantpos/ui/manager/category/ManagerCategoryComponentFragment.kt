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

//    Kết nối với   // 6.  Code cho ADD Button

//    Bình thường khi 1 Activity nó kết thúc
//    Bình thường chỉ thấy truyền dữ liệu từ màn này sang màn khác để chuyển màn
//    Còn 1 sự kiện nữa là lúc nó kết thúc nó có thể đáp data cho bất kì màn nào mà trước đấy được quay trở lại
//    Line 93: intent.action = Intent.ACTION_GET_CONTENT
//    Hệ thống có rất nhiều action: Có cả action setting luôn.
//    Acction của mình lấy content là mình chọn dữ liệu của máy
//    Lúc này Nếu không có  intent.type = "image/*"
//    thì nó sẽ mở hẳn bộ nhớ máy ra cho mình luôn
//    Mình có thể thay bằng intent.type = "*/*" (Kiểu/loại)-> Sẽ hiển thị tất cả những gì mà mình có thể lấy được
//    * Tất cả những file: video (mp4, mp3,...), image, GIF
//    image/*  --> * là JPG, JPEG,PNG, GIF,...

//    onActivityResult  --> Là 1 màn của Activity luôn.
//    Đoạn code bên dưới không phải do thằng // 6.  Code cho ADD Button trả ra đâu
//    Mình có thể tạo ra sự kiện onActivityResult. Nó là hàm chuyển ấy
//    Trước khi kết thúc mình chỉ cần gọi ấy thôi là nó sẽ tự chuyển ra
//    Lo Search Google đi chứ!. Nó có tính phí đâu (create, get, how to  ... android studio)
//    Đọc của bọn android thường khó hiểu lắm. Chọn StackOverFlow, để ý những link nhỏ bên dưới!
//
//    //6:  startActivityForResult(Intent.createChooser(intent, "Select Photo Quang"), 101)
//    onActivityResult --> Hứng lại luôn luôn theo cặp:
//    RequestCode: Cái mình gọi ra để đánh dấu. Nhiều thằng lắm nên cần RequestCode để phân biệt
//    Số RequestCode là do mình tạo ra để đánh dấu sự kiện startActivityForResult của mình
//    Vì mỗi thời điểm nó có thể trả ra rất nhiều màn hình được trả về
//    resultCode == AppCompatActivity.RESULT_OK là cái trả ra của hệ thống, cứ = OK là được
//    Chọc vào trong hàm mà xem
//
//    Mình chia if
//        1. Là của mình (Xe của mình và thùng xe chứa data, thì mình mới xử lý tiếp) và nó có tha data về đã


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