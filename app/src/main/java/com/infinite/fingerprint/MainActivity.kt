package com.infinite.fingerprint

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.abc)
            .setOnClickListener {
                FingerprintUtil.getInstance(applicationContext)
                    .authenticate(object : FingerprintVerifyCallback {
                        override fun onHardwareUndetected() {
                            Log.e("finger", "onHardwareUndetected")
                        }

                        override fun onHasNoEnrollFingerprint() {
                            val intent =
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                    Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                                } else {
                                    Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                                }
                            startActivity(intent)
                        }

                        override fun onSuccess() {
                            Log.e("finger", "onSuccess")

                        }

                        override fun onFail() {
                            Log.e("finger", "onFail")

                        }

                        override fun onCancel() {
                            Log.e("finger", "onCancel")

                        }

                        override fun onUsePassword() {
                            Log.e("finger", "onUsePassword")

                        }

                        override fun onError(errMsgId: Int, errString: CharSequence?) {
                            Toast.makeText(this@MainActivity, errString, Toast.LENGTH_LONG).show()

                        }

                        override fun onHelp(helpMsgId: Int, helpString: CharSequence?) {
                        }
                    })
            }

    }


}