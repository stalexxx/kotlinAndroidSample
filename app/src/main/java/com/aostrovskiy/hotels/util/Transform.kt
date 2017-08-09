package com.aostrovskiy.hotels.util

import android.content.Context
import android.graphics.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.bitmap.BitmapResource

class Transform(val context: Context) : Transformation<Bitmap> {
    override fun getId(): String {
        return Constant.GLIDE_TRANSFORM_ID
    }

    override fun transform(resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        val source = resource.get()

        val width = source.width
        val height = source.height

        val bitmapPool = Glide.get(context).bitmapPool
        var bitmap: Bitmap? = bitmapPool.get(width, height, Bitmap.Config.ARGB_8888)
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap!!)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val fl = Constant.HOTEL_IMAGE_MARGIN_WIDTH
        canvas.drawRoundRect(RectF(fl, fl, width - fl, height - fl), 0f, 0f, paint)
        return BitmapResource.obtain(bitmap, bitmapPool)

    }

}