package com.app.mndalakanm.ui.Home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import  com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentHomeBinding
import com.app.mndalakanm.utils.SharedPref
import java.util.concurrent.TimeUnit

import android.Manifest
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.vilborgtower.user.utils.Constant
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class HomeFragment : Fragment() {
    var myCountDownTimer: MyCountDownTimer? = null
lateinit var binding: FragmentHomeBinding
lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        sharedPref= SharedPref(requireContext())
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

        return binding.root
    }
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
                uploadImageToFirebase(imageUri)
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

    fos?.use {
        // Finally writing the bitmap to the output stream that we opened
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        binding.ivAddPost.setImageBitmap(bitmap)
        Toast.makeText(requireContext() , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
    }
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
}
