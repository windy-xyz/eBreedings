package com.wcs.ebreedings.api

import com.wcs.ebreedings.data.model.LoginResponse
import com.wcs.ebreedings.ui.sync.SyncResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("loginAuth")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("estate") estate: String,
        @Field("updatemaster") updatemaster: Int
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("InsertDataMobile")
    fun insertData(
        @Field("json") json: String
    ): Call<SyncResponse>

}
