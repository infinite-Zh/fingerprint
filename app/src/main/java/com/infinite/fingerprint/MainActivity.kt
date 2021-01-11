package com.infinite.fingerprint

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.RequiresPermission
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.abc)
            .setOnClickListener {
                FingerprintUtil.getInstance(this)
                    .authenticate(object :FingerprintVerifyCallback{
                        override fun onHardwareUndetected() {
                            Log.e("finger","onHardwareUndetected")
                        }

                        override fun onHasNoEnrollFingerprint() {
                            Log.e("finger","onHasNoEnrollFingerprint")

                        }

                        override fun onSuccess() {
                            Log.e("finger","onSuccess")

                        }

                        override fun onFail() {
                            Log.e("finger","onFail")

                        }

                        override fun onCancel() {
                            Log.e("finger","onCancel")

                        }

                        override fun onUsePassword() {
                            Log.e("finger","onUsePassword")

                        }

                        override fun onError(errMsgId: Int, errString: CharSequence?) {
                        }

                        override fun onHelp(helpMsgId: Int, helpString: CharSequence?) {
                        }
                    })
            }

    }



}