package com.gads2020.practice.utils

import android.util.Log
import com.gads2020.practice.BuildConfig

object Utils {
    fun showLog(msg: String, tag:String = "Gads2020") {
        if(BuildConfig.DEBUG) Log.e(tag,msg)
    }

}