package com.infinite.fingerprint

interface IFingerprint {


    fun authenticate(
        callback: FingerprintVerifyCallback
    )
}