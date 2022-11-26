package com.ciklum.weatherapp.utils.preferences


interface PrefStore {

    fun getString(key: String, def: String = ""): String
    fun saveString(key: String, value: String?)

    fun getBoolean(key: String, def: Boolean = false): Boolean
    fun saveBoolean(key: String, value: Boolean?)

    fun getInt(key: String, value: Int = 0): Int
    fun saveInt(key: String, value: Int)

    fun addObjectToCache(key: String, value: Any?)
    fun getObjectFromCache(key: String): Any?

    fun remove(key: String)

}