package com.orz.infos.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.orz.infos.Constant
import java.io.File
import java.io.InputStream

/**
 * Created by Administrator on 2017/10/24.
 */
@GlideModule
class GlideCacheModule: AppGlideModule(){

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        var file = context!!.getExternalFilesDir(Constant.IMAGE_CACHE_PATH)
        if (file != null) {
            if (!file.exists()) {
                file.mkdirs()
            }
            var path = file.absolutePath
            builder!!.setDiskCache(DiskLruCacheFactory(path, Constant.DEFAULT_IMAGE_CACHE_SIZE))
        }
    }


    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        registry!!.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }
}