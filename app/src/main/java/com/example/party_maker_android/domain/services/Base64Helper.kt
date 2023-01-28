package com.example.party_maker_android.domain.services

import android.R.string
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


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

        fun isBase64StringValid(base64: String?): Boolean{
            if(base64 == null) {
                return false
            }
            if(base64.isEmpty()){
                return false
            }

            val pattern =
                "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$"
            val r: Pattern = Pattern.compile(pattern)
            val m: Matcher = r.matcher(base64)
            return m.find()
        }
    }
}