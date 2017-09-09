package com.mmdteam.beautygirl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.math.BigDecimal

/**
 * Created by brain on 2017/9/8.
 * 文件相关
 */
object FileUtils {


    fun cleanInternalCache(context: Context) {
        deleteFilesByDirectory(context.cacheDir)
    }

    @SuppressLint("SdCardPath")
    fun cleanDatabases(context: Context) {
        deleteFilesByDirectory(File("/data/data/"
                + context.getPackageName() + "/databases"))
    }

    @SuppressLint("SdCardPath")
    fun cleanSharedPreference(context: Context) {
        deleteFilesByDirectory(File("/data/data/"
                + context.getPackageName() + "/shared_prefs"))
    }

    fun cleanDatabaseByName(context: Context, dbName: String) {
        context.deleteDatabase(dbName)
    }

    fun cleanFiles(context: Context) {
        deleteFilesByDirectory(context.filesDir)
    }

    fun cleanExternalCache(context: Context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.externalCacheDir)
        }
    }

    fun cleanCustomCache(filePath: String) {
        deleteFilesByDirectory(File(filePath))
    }

    fun cleanApplicationData(context: Context, vararg filePath: String) {
        cleanInternalCache(context)
        cleanExternalCache(context)
//        cleanDatabases(context)
//        cleanSharedPreference(context)
        cleanFiles(context)
        if (false) {
            return
        }
        filePath.forEach {
            cleanCustomCache(it)
        }

    }

    /**
     * 删除文件夹下面的文件
     */
    fun deleteFilesByDirectory(directory: File) {

        if (directory.exists() && directory.isDirectory) {
            directory.listFiles().forEach {
                deleteFolderFile(it.absolutePath, true)
            }
        }

    }

    /**
     * 获取文件大小
     */
    fun getFolderSize(file: File): Long {
        var size = 0L;
        try {
            val listFiles = file.listFiles()
            listFiles.forEach {
                if (it.isDirectory) {
                    size = size + getFolderSize(it)
                } else {
                    size = size + it.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    /**
     * 删除指定目录
     */
    fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {

        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {
                    val listFiles = file.listFiles()
                    listFiles.forEach {
                        deleteFolderFile(it.absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (file.isFile) {
                        file.delete()
                    } else {
                        if (file.listFiles().size == 0) {
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun getFormatSize(size: Double): String {

        val value = 1024

        val kiloByte = size / value
        if (kiloByte < 1) {
            return size.toString() + "Byte"
        }

        val megaByte = kiloByte / value
        if (megaByte < 1) {
            return BigDecimal(kiloByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }

        val gigaByte = megaByte / value
        if (gigaByte < 1) {
            return BigDecimal(megaByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }

        val teraBytes = gigaByte / value
        if (teraBytes < 1) {
            return BigDecimal(gigaByte).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }

        return BigDecimal(teraBytes).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }


    fun getCacheSize(vararg files: File): String {
        var size = 0L
        files.forEach {
            size += getFolderSize(it)
        }

        return getFormatSize(size.toDouble())
    }


}