package com.ciklum.weatherapp.utils.preferences

import android.content.SharedPreferences
import java.util.*

class SharedPrefStore(sharedPrefStore: SharedPreferences) : PrefStore {

    companion object {
        private val sObjectCache = HashMap<String, Any?>()
    }

    private val sharedPrefs =sharedPrefStore

    override fun getInt(key: String, value: Int): Int = sharedPrefs.getInt(key, value)

    override fun saveInt(key: String, value: Int) {
        sharedPrefs.edit().putInt(key, value).apply()
    }

    override fun addObjectToCache(key: String, value: Any?) {
        sObjectCache[key] = value
    }

    override fun getObjectFromCache(key: String): Any? {
        return sObjectCache[key]
    }

    override fun remove(key: String) {
        sharedPrefs.edit().remove(key).apply()
    }

    override fun getBoolean(key: String, def: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, def)
    }

    override fun saveString(key: String, value: String?) {
        sharedPrefs.edit().putString(key, value).apply()
    }

    override fun saveBoolean(key: String, value: Boolean?) {
        sharedPrefs.edit().putBoolean(key, value ?: false).apply()
    }


    override fun getString(key: String, def: String): String {
        return sharedPrefs.getString(key, def) ?: def
    }

}