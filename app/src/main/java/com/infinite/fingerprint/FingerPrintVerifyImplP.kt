package com.infinite.fingerprint

import android.content.Context
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import javax.crypto.Cipher

/***
 * 29以下指纹识别
 */
@RequiresApi(api = Build.VERSION_CODES.P)
class FingerprintVerifyImplP(private val context: Context) : IFingerprint {

    private val biometricPrompt = BiometricPrompt.Builder(context)

    private lateinit var callback: FingerprintVerifyCallback

    private var mCypherHelper: CipherHelper = CipherHelper()

    private lateinit var cryptoObject: BiometricPrompt.CryptoObject

    override fun authenticate(callback: FingerprintVerifyCallback) {
        var cipher: Cipher?
        try {
            cipher = mCypherHelper.createCipher()
            cryptoObject = BiometricPrompt.CryptoObject(cipher!!)
            this.callback = callback
            cancellationSignal.setOnCancelListener {
                callback.onCancel()
            }
            biometricPrompt.setTitle("指纹识别")
                .setNegativeButton("取消", context.mainExecutor, { dialogInterface, i ->
//                callback.onCancel()
                })
                .build().authenticate(
                    cryptoObject,
                    cancellationSignal,
                    context.mainExecutor,
                    authenticationCallback
                )
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onHasNoEnrollFingerprint()
        }

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
                if (errMsgId == BiometricPrompt.BIOMETRIC_ERROR_CANCELED) {
                    callback.onCancel()
                } else {
                    callback.onError(errMsgId, errString)
                }

            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpMsgId, helpString)
                callback.onHelp(helpMsgId, helpString)
            }
        }


}