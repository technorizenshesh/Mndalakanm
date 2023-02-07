package com.app.mndalakanm.ui.Home

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.app.mndalakanm.Model.SuccessScreenshotRes
import com.app.mndalakanm.adapter.AdapterScreenshotList
import com.app.mndalakanm.retrofit.ApiClient
import com.app.mndalakanm.utils.*
import com.cityoneprovider.retrofit.ProviderInterface
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentHomeBinding
import com.vilborgtower.user.utils.Constant
import com.vilborgtower.user.utils.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment()  , ScreenShotClickListener {
    var myCountDownTimer: MyCountDownTimer? = null
lateinit var binding: FragmentHomeBinding
lateinit var sharedPref: SharedPref
    private lateinit var apiInterface: ProviderInterface
    var profileImage: File? = null
    private var screenshotRes: ArrayList<SuccessScreenshotRes.ScreenshotList>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        sharedPref= SharedPref(requireContext())
        apiInterface = ApiClient.getClient(requireContext())!!.create(ProviderInterface::class.java)

        myCountDownTimer = MyCountDownTimer(60000, 1000)
        myCountDownTimer!!.start()
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        binding.btn.setOnClickListener {
            val bitmap = getScreenShotFromView(requireActivity().getWindow().getDecorView().getRootView())
            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }
        }
        //checkOverlayPermission()
         getScreenShots();
        return binding.root
    }

  /*  override fun onResume() {
        startService()
        super.onResume()
    }
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(requireActivity())) {
            // send user to the device settings
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

    // method for starting the service
    fun startService() {
        if (Settings.canDrawOverlays(requireActivity())) {
            // start the service based on the android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(requireContext(),Intent(requireContext(), ForegroundService::class.java))
            } else
            { requireActivity().startService(Intent(requireContext(), ForegroundService::class.java))
             }
        }else{
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }*/

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            val progress = (millisUntilFinished / 1000).toInt()
            binding.progressBar.setProgress(binding.progressBar.getMax() - progress)
            binding.cloctTime.setText(converter(millisUntilFinished))
        }

        override fun onFinish() {
            //finish()
        }
    }

    fun converter(millis: Long): String =
        String.format(
            " %02d : %02d : %02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )


private fun getScreenShotFromView(v: View): Bitmap? {
    // create a bitmap object
    var screenshot: Bitmap? = null
    try {
        // inflate screenshot object
        // with Bitmap.createBitmap it
        // requires three parameters
        // width and height of the view and
        // the background color
        screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        // Now draw this bitmap on a canvas
        val canvas = Canvas(screenshot)
        v.draw(canvas)
    } catch (e: Exception) {
        Log.e("GFG", "Failed to capture screenshot because:" + e.message)
    }
    // return the bitmap
    return screenshot
}

// this method saves the image to gallery
private fun saveMediaToStorage(bitmap: Bitmap) {
    // Generating a file name
    val filename = "${System.currentTimeMillis()}.jpg"

    // Output stream
    var fos: OutputStream? = null

    // For devices running android >= Q
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // getting the contentResolver
        requireActivity().contentResolver?.also { resolver ->

            // Content resolver will process the contentvalues
            val contentValues = ContentValues().apply {

                // putting file information in content values
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            // Inserting the contentValues to
            // contentResolver and getting the Uri
            val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (imageUri != null) {
             //   uploadImageToFirebase(imageUri)
            }
            // Opening an outputstream with the Uri that we got
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        // These for devices running on android < Q
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }
        // Finally writing the bitmap to the output stream that we opened
       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        binding.ivAddPost.setImageBitmap(bitmap)
    val tempUri: Uri = ProjectUtil.getImageUri(requireContext(), bitmap)!!
    val imag = RealPathUtil.getRealPath(requireContext(), tempUri)
    profileImage = File(imag)
AddDetails(profileImage!!)
    Toast.makeText(requireContext() , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()

}

    private fun uploadImageToFirebase(fileUri: Uri) {
        val fileName = sharedPref.getStringValue(Constant.CHILD_ID)+ sharedPref.getStringValue(Constant.CHILD_NAME)+Calendar.getInstance().time+".jpg"
        val database = FirebaseDatabase.getInstance()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")
        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                    }
                })

            ?.addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })
    }

    private fun AddDetails(mage: File) {
        DataManager.instance
            .showProgressMessage(requireActivity(), getString(R.string.please_wait))
        val profileFilePart: MultipartBody.Part
        val attachmentEmpty: RequestBody
        if (mage == null) {
            attachmentEmpty = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
            profileFilePart = MultipartBody.Part.createFormData(
                "attachment",
                "", attachmentEmpty
            )
        } else {
            profileFilePart = MultipartBody.Part.createFormData("image", mage.name, RequestBody.create("image/*".toMediaTypeOrNull(), mage!!)) }
        val namedata = RequestBody.create("text/plain".toMediaTypeOrNull(),
            sharedPref.getStringValue(Constant.CHILD_ID).toString())
        val register = RequestBody.create("text/plain".toMediaTypeOrNull(),
            sharedPref.getStringValue(Constant.USER_ID).toString()
        )

        apiInterface.add_screenshot(
            register, namedata , profileFilePart
        ).enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                DataManager.instance.hideProgressMessage()
                try {
                    val responseString = response.body()!!.string()
                    val jsonObject = JSONObject(responseString)
                    val message  = jsonObject.getString("message")
                    if (jsonObject.getString("status") == "1") {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        getScreenShots()
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Exception = " + e.message, Toast.LENGTH_SHORT).show()
                    Log.e("Exception", "Exception = " + e.message)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                DataManager.instance.hideProgressMessage()
                Log.e(TAG, "onFailure: "+t.message )
                Log.e(TAG, "onFailure: "+t.cause )
                Log.e(TAG, "onFailure: "+t.localizedMessage )
            }

        })


    }
    private fun getScreenShots() {
        DataManager.instance
            .showProgressMessage(requireActivity(), getString(R.string.please_wait))
        val map = HashMap<String, String>()
           map["parent_id"] =  sharedPref.getStringValue(Constant.USER_ID).toString()
           map["child_id"] =  sharedPref.getStringValue(Constant.CHILD_ID).toString()
        Timber.tag(ContentValues.TAG).e("Login user Request = %s", map)
        apiInterface.get_screenshot(map).enqueue(object : Callback<SuccessScreenshotRes?> {
            override fun onResponse(call: Call<SuccessScreenshotRes?>, response: Response<SuccessScreenshotRes?>) {
                DataManager.instance.hideProgressMessage()
                try {
                    if (response.body() != null && response.body()?.status.equals("1")) {
                        screenshotRes?.clear()
                        screenshotRes = response.body()!!.result
                        val adapterRideOption =
                            AdapterScreenshotList(requireActivity(),
                                screenshotRes,this@HomeFragment )
                        val numberOfColumns = 3
                        binding.childList.setLayoutManager(GridLayoutManager(requireActivity(), numberOfColumns))


                        binding.childList.adapter = adapterRideOption
                    }else{
                        Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()

                    }
                } catch (e: Exception) {
                    DataManager.instance.hideProgressMessage()
                    Toast.makeText(context, "Exception = " + e.message, Toast.LENGTH_SHORT).show()
                    Timber.tag("Exception").e("Exception = %s", e.message)
                }
            }

            override fun onFailure(call: Call<SuccessScreenshotRes?>, t: Throwable) {
                DataManager.instance.hideProgressMessage()
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
            }
        })
    }

    override fun onClick(position: Int, model: SuccessScreenshotRes.ScreenshotList) {

    }

}
