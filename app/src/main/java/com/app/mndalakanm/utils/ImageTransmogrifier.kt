package com.app.mndalakanm.utils

import android.graphics.Bitmap
import android.media.ImageReader
import android.view.Surface
import java.io.ByteArrayOutputStream


/**
 * Created by Ravindra Birla on 07,February,2023
 */
class ImageTransmogrifier :ImageReader.OnImageAvailableListener {

    private val width = 0
    private val height = 0
    private val imageReader: ImageReader? = null
    private val svc: ScreenshotServiceOld? = null
    private var latestBitmap: Bitmap? = null
    override fun onImageAvailable(reader: ImageReader?) {
        val image = imageReader!!.acquireLatestImage()
        if (image != null) {
            val planes = image.planes
            val buffer = planes[0].buffer
            val pixelStride = planes[0].pixelStride
            val rowStride = planes[0].rowStride
            val rowPadding = rowStride - pixelStride * width
            val bitmapWidth = width + rowPadding / pixelStride
            if (latestBitmap == null || latestBitmap!!.getWidth() != bitmapWidth
                || latestBitmap!!.getHeight() != height) {
                latestBitmap?.recycle()
                latestBitmap = Bitmap.createBitmap(
                    bitmapWidth,
                    height, Bitmap.Config.ARGB_8888
                )
            }
            latestBitmap!!.copyPixelsFromBuffer(buffer)
            image.close()
            val baos = ByteArrayOutputStream()
            val cropped = Bitmap.createBitmap(latestBitmap!!, 0, 0, width, height)
            cropped.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val newPng = baos.toByteArray()
            svc?.processImage(newPng)
        }
    }

    fun getSurface(): Surface? {
        return imageReader!!.surface
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun close() {
        imageReader!!.close()
    }

}