package com.app.mndalakanm.ui.setupKid

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cityoneprovider.retrofit.ProviderInterface
import com.app.mndalakanm.Model.SuccessAddChildRes
import  com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentChildDetailsBinding
import com.app.mndalakanm.retrofit.ApiClient
import com.app.mndalakanm.utils.DataManager
import com.app.mndalakanm.utils.ProjectUtil
import com.app.mndalakanm.utils.SharedPref
import com.vilborgtower.user.utils.Constant
import com.vilborgtower.user.utils.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ChildDetailsFragment : Fragment() {
    private lateinit var apiInterface: ProviderInterface
    lateinit var sharedPref: SharedPref
    lateinit var binding: FragmentChildDetailsBinding
    var profileImage: File? = null

    private val GALLERY = 0;
    private val CAMERA = 1;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_child_details, container, false
        )

        sharedPref = SharedPref(requireContext())
        apiInterface = ApiClient.getClient(requireContext())!!.create(ProviderInterface::class.java)

        binding.header.imgHeader.setOnClickListener {
            // activity?.onBackPressed()
            sharedPref.clearAllPreferences()
            Navigation.findNavController(binding.root).navigate(R.id.action_menu_to_login_type)

        }
        binding.btnSignIn.setOnClickListener {
            var name = binding.editName.text.toString()
            var age = binding.agePick.text.toString()

            if (name.equals("", true)) {
                binding.editName.error = getString(R.string.empty)
            } else if (age.equals("", true)) {
                binding.agePick.error = getString(R.string.empty)
            } else if (profileImage == null) {
                Toast.makeText(requireContext(), "Please Add Image", Toast.LENGTH_SHORT).show()
            } else {
                AddDetails(name, age);

            }
        }
        binding.agePick.setOnClickListener {
            openPicker()

        }
        binding.addImage.setOnClickListener {
            if (ProjectUtil.checkPermissions(requireActivity())) {
                showPictureDialog()
            } else {
                ProjectUtil.requestPermissions(requireActivity())
            }
        }
        return binding.root
    }

    fun openPicker() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.getAttributes()?.windowAnimations =
            android.R.style.Widget_Material_ListPopupWindow
        dialog.setContentView(R.layout.number_picker_dialog)
        val lp = WindowManager.LayoutParams()
        val window: Window = dialog.getWindow()!!
        lp.copyFrom(window.getAttributes())
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setAttributes(lp)
        val no_btn: TextView = dialog.findViewById(R.id.no_btn)
        val yes_btn: TextView = dialog.findViewById(R.id.yes_btn)
        val numberPicker: NumberPicker = dialog.findViewById(R.id.dialog_number_picker)
        numberPicker.maxValue = 18
        numberPicker.minValue = 3
        numberPicker.wrapSelectorWheel = false

        numberPicker.setOnValueChangedListener { numberPicker, i, i1 ->
            Log.e(TAG, "openPicker: iiiii---" + i)
            Log.e(TAG, "openPicker: 11111---" + i1)
            //  Toast.makeText(requireContext(), ""+i, Toast.LENGTH_SHORT).show()

        }
        no_btn.setOnClickListener { v1: View? -> dialog.dismiss() }
        yes_btn.setOnClickListener { v1: View? ->
            binding.agePick.setText(numberPicker.value.toString())
            dialog.dismiss()
        }
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    @SuppressLint("HardwareIds")
    private fun AddDetails(name: String, age: String) {
        DataManager.instance
            .showProgressMessage(requireActivity(), getString(R.string.please_wait))

        val profileFilePart: MultipartBody.Part
        val attachmentEmpty: RequestBody
        if (profileImage == null) {
            attachmentEmpty = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
            profileFilePart = MultipartBody.Part.createFormData(
                "attachment",
                "", attachmentEmpty
            )
        } else {
            profileFilePart = MultipartBody.Part.createFormData(
                "image",
                profileImage!!.name, RequestBody.create(
                    "image/*".toMediaTypeOrNull(), profileImage!!
                )
            )
        }
        val agedata = RequestBody.create("text/plain".toMediaTypeOrNull(), age)
        val namedata = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val register = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            sharedPref.getStringValue(Constant.CHILD_ID).toString()
        )
        apiInterface.add_child(
            register, agedata,
            namedata, profileFilePart
        ).enqueue(object : Callback<SuccessAddChildRes?> {
            override fun onResponse(
                call: Call<SuccessAddChildRes?>,
                response: Response<SuccessAddChildRes?>
            ) {
                DataManager.instance.hideProgressMessage()
                try {
                    if (response.body()?.status.equals("1", true)) {
                        val bundle = Bundle()
                        bundle.putString("type", "child")
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_splash_to_child_permission_fragment, bundle)
                        sharedPref.setStringValue(Constant.CHILD_NAME, name)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Exception = " + e.message, Toast.LENGTH_SHORT).show()
                    Log.e("Exception", "Exception = " + e.message)
                }
            }

            override fun onFailure(call: Call<SuccessAddChildRes?>, t: Throwable) {
                DataManager.instance.hideProgressMessage()
            }

        })


    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> {
                    val galleryIntent = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    galleryIntent.type = "image/*"
                    startActivityForResult(galleryIntent, GALLERY)
                }
                1 -> {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (cameraIntent.resolveActivity(requireActivity().packageManager) != null)
                        startActivityForResult(cameraIntent, CAMERA)
                }
            }
        }
        pictureDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val path: String = RealPathUtil.getRealPath(requireActivity(), data!!.data)!!
                profileImage = File(path)
                binding.addImage.setImageURI(Uri.parse(path))
            }
        } else if (requestCode == CAMERA) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                try {
                    if (data != null) {
                        val extras = data.extras
                        val bitmapNew = extras!!["data"] as Bitmap
                        val imageBitmap: Bitmap =
                            BITMAP_RE_SIZER(bitmapNew, bitmapNew!!.width, bitmapNew!!.height)!!
                        val tempUri: Uri = ProjectUtil.getImageUri(requireContext(), imageBitmap)!!
                        val image = RealPathUtil.getRealPath(requireContext(), tempUri)
                        profileImage = File(image)
                        Log.e("sgfsfdsfs", "profileImage = $profileImage")
                        binding.addImage.setImageURI(Uri.parse(image))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun BITMAP_RE_SIZER(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val ratioX = newWidth / bitmap.width.toFloat()
        val ratioY = newHeight / bitmap.height.toFloat()
        val middleX = newWidth / 2.0f
        val middleY = newHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bitmap,
            middleX - bitmap.width / 2,
            middleY - bitmap.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )
        return scaledBitmap
    }


}