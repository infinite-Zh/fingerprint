package com.infinite.fingerprint

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintUtil(private val context: Context) : IFingerprint {

    companion object {
        private var instance: FingerprintUtil? = null
        fun getInstance(context: Context): FingerprintUtil {
            return instance ?: synchronized(FingerprintUtil::class) {
                return instance ?: FingerprintUtil(context).also {
                    instance = it
                }
            }

        }
    }

    override fun authenticate(callback: FingerprintVerifyCallback) {
        val sdk = Build.VERSION.SDK_INT
        when {
            sdk < 23 -> {
                callback.onHardwareUndetected()
            }
            sdk < Build.VERSION_CODES.P -> {
                FingerprintVerifyImplM(context).authenticate(callback)
            }
            sdk >= Build.VERSION_CODES.P -> {
                FingerprintVerifyImplP(context).authenticate(callback)
            }
        }
    }
}