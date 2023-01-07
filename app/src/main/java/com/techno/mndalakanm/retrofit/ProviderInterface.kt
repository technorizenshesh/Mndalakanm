package com.cityoneprovider.retrofit

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProviderInterface {

    @FormUrlEncoded
    @POST(ApiConstant.LOGIN)
    fun loginProvider(@FieldMap map: Map<String,String> ): Call<ResponseBody>




    @FormUrlEncoded
    @POST(ApiConstant.GET_PROFILE)
    fun getProviderProfile(@FieldMap params: Map<String, String>): Call<ResponseBody>


    @FormUrlEncoded
    @POST(ApiConstant.GET_PROVIDER_SHOP)
    fun getProviderShops(@FieldMap params: Map<String, String>): Call<ResponseBody>



}