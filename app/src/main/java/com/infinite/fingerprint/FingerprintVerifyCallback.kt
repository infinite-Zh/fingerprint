package com.infinite.fingerprint

interface FingerprintVerifyCallback {

    fun onHardwareUndetected()

    fun onHasNoEnrollFingerprint()

    fun onSuccess()

    fun onFail()

    fun onCancel()

    fun onUsePassword()



}