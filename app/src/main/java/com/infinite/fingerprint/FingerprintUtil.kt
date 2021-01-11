package com.infinite.fingerprint

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal

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
                callback.onFail()
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