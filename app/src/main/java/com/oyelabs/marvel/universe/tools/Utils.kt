package com.oyelabs.marvel.universe.tools

import java.math.BigInteger
import java.security.MessageDigest
class Utils {
    private fun md5(string: String): String{
        val messageDigest= MessageDigest.getInstance("MD5")
        messageDigest.update(string.toByteArray())
        val digest= messageDigest.digest()
        val bigInt= BigInteger(1, digest)
        var hashText: String= bigInt.toString(16)
        while (hashText.length< 32){
            hashText= """0$hashText"""
        }
        return hashText
    }
    fun getGenKeyUser(): Map<String, String> {
        val timeStamp= (System.currentTimeMillis()/1000).toString()
        val hash: String= md5(timeStamp+ PRIVATE_KEY + PUBLIC_KEY)
        return hashMapOf(TIME_STAMP to timeStamp, API_KEY to PUBLIC_KEY, HASH to hash)
    }
    companion object {
        private const val PUBLIC_KEY= "62129b351da613d20961d26809f9e599"
        private const val PRIVATE_KEY= "26aeef0fc9fa44461431097c62e31f9f6be9d0aa"
        private const val TIME_STAMP= "ts"
        private const val API_KEY= "apikey"
        private const val HASH= "hash"
    }
}