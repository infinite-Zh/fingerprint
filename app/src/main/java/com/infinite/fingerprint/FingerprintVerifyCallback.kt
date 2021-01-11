package com.infinite.fingerprint

interface FingerprintVerifyCallback {

    fun onHardwareUndetected()

    fun onHasNoEnrollFingerprint()

    fun onSuccess()

    fun onFail()

    fun onCancel()

    fun onUsePassword()

    fun onError(errMsgId: Int, errString: CharSequence?)

    fun onHelp(helpMsgId: Int, helpString: CharSequence?)


}