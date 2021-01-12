package com.infinite.fingerprint

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import javax.crypto.Cipher

/***
 * 28以下指纹识别
 */
@RequiresApi(Build.VERSION_CODES.M)
class FingerprintVerifyImplM(context: Context) : IFingerprint {

    private val fmCompat = FingerprintManagerCompat.from(context)

    private lateinit var callback: FingerprintVerifyCallback
    private var mCypherHelper: CipherHelper = CipherHelper()

    override fun authenticate(callback: FingerprintVerifyCallback) {
        this.callback = callback
        if (!fmCompat.isHardwareDetected) {
            callback.onHardwareUndetected()
            return
        }
        if (!fmCompat.hasEnrolledFingerprints()) {
            callback.onHasNoEnrollFingerprint()
            return
        }

        var cipher: Cipher? = null
        try {
            cipher = mCypherHelper.createCipher()
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onHasNoEnrollFingerprint()
        }

        fmCompat.authenticate(
            FingerprintManagerCompat.CryptoObject(cipher!!),
            0,
            cancellationSignal,
            authenticationCallback,
            null
        )
    }

    private val cancellationSignal = CancellationSignal()

    private val authenticationCallback =
        object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                callback.onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onFail()
            }

            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                super.onAuthenticationError(errMsgId, errString)
                if (errMsgId == FingerprintManager.FINGERPRINT_ERROR_CANCELED) {
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