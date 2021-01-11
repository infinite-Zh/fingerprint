package com.infinite.fingerprint

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi

/***
 * 29以下指纹识别
 */
@RequiresApi(api = Build.VERSION_CODES.P)
class FingerprintVerifyImplP(private val context: Context) : IFingerprint {

    private val biometricPrompt = BiometricPrompt.Builder(context)

    private lateinit var callback: FingerprintVerifyCallback

    private var mCypherHelper: CipherHelper = CipherHelper()

    private var cryptoObject = BiometricPrompt.CryptoObject(mCypherHelper.createCipher())

    override fun authenticate(callback: FingerprintVerifyCallback) {
        this.callback=callback
        biometricPrompt.setTitle("指纹识别")
            .setNegativeButton("取消", context.mainExecutor, { dialogInterface, i ->
            })
            .build().authenticate(
                cryptoObject,
                cancellationSignal,
                context.mainExecutor,
                authenticationCallback
            )
    }

    private val cancellationSignal = CancellationSignal()

    private val authenticationCallback =
        object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                callback.onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onFail()
            }

            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                super.onAuthenticationError(errMsgId, errString)
                callback.onFail()
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpMsgId, helpString)

            }
        }


}