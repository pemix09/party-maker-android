package com.example.party_maker_android.domain.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


class Base64Helper {
    companion object{
        fun getBase64StringFromBitmap(bitmapImage: Bitmap): String{
            val outputStream = ByteArrayOutputStream()
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }

        fun getBitmapFromBase64(base64Image: String): Bitmap{
            val decodedBytes: ByteArray = Base64.decode(
                base64Image.substring(base64Image.indexOf(",") + 1),
                Base64.DEFAULT
            )

            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }
}