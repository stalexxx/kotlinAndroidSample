package com.aostrovskiy.hotels.di.module


import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.aostrovskiy.hotels.di.ApplicationScope
import com.aostrovskiy.hotels.util.Transform
import com.aostrovskiy.hotels.view.card.CardFillRoutine
import com.bumptech.glide.load.Transformation
import dagger.Module
import dagger.Provides
import jp.wasabeef.glide.transformations.CropCircleTransformation
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideContext(): Context = this.application

    @Provides
    @Singleton
    @ApplicationScope
    fun provideTransform(context: Context): Transformation<Bitmap> = CropCircleTransformation(context)

    @Provides
    @Singleton
    @ApplicationScope
    fun provideCardFiller(context: Context): CardFillRoutine = CardFillRoutine(context)
}
