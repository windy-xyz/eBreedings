package com.wcs.ebreedings.api

interface CallbackInterface<T>{
    fun onDataResponse(data: T?)
    fun onDataFailure(data: String?)
}
