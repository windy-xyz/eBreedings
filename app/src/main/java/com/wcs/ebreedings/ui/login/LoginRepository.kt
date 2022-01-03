package com.wcs.ebreedings.ui.login

import android.content.Context
import com.wcs.ebreedings.api.CallbackInterface
import com.wcs.ebreedings.data.model.LoginResponse
import com.wcs.ebreedings.data.storage.Pref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepository(val dataSource: LoginDataSource) {

    fun login(username: String, password: String, estate: String, context: Context, callback: CallbackInterface<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val updateMaster = getUpdateMaster(context)
            dataSource.login(username, password, estate, updateMaster, context, object : CallbackInterface<LoginResponse> {
                override fun onDataResponse(data: LoginResponse?) {
                    callback.onDataResponse(data?.result)
                    data?.currentMaster?.let {
                        setLoggedInUser(username, it, context)
                    }
                }

                override fun onDataFailure(data: String?) {
                    callback.onDataFailure(data)
                }
            })
        }
    }

    private fun getUpdateMaster(context: Context): Int {
        val pref = Pref(context)
        return pref.getUpdateMaster()
    }

    private fun setLoggedInUser(username: String, updateMaster: Int ,context: Context) {
        val pref = Pref(context)
        pref.saveUsername(username)
        pref.saveIsLogin(true)
        pref.saveUpdateMaster(updateMaster)
    }
}