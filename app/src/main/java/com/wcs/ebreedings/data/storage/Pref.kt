package com.wcs.ebreedings.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.wcs.ebreedings.R

class Pref(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name) + "_pref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // SharedPreferences variables
    private val keyIsLogin = "is_login"
    private val keyUsername = "username"
    private val keyUpdateMaster = "update_master"
    private val keySupervisor = "supervisor"
    private val keySurveyor = "surveyor"

    fun saveIsLogin(isLogin: Boolean) {
        editor.putBoolean(keyIsLogin, isLogin)
        editor.apply()
    }

    fun getIsLogin(): Boolean {
        return sharedPreferences.getBoolean(keyIsLogin, false)
    }

    fun saveUsername(username: String) {
        editor.putString(keyUsername, username)
        editor.apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(keyUsername, "").toString()
    }

    fun doLogout() {
        saveUsername("")
        saveIsLogin(false)
    }

    fun saveUpdateMaster(updateMaster: Int) {
        editor.putInt(keyUpdateMaster, updateMaster)
        editor.apply()
    }

    fun getUpdateMaster(): Int {
        return sharedPreferences.getInt(keyUpdateMaster, 0)
    }

    fun saveSupervisor(supervisor: String) {
        editor.putString(keySupervisor, supervisor)
        editor.apply()
    }

    fun getSupervisor(): String {
        return sharedPreferences.getString(keySupervisor, "").toString()
    }

    fun saveSurveyor(surveyor: String) {
        editor.putString(keySurveyor, surveyor)
        editor.apply()
    }

    fun getSurveyor(): String {
        return sharedPreferences.getString(keySurveyor, "").toString()
    }
}
