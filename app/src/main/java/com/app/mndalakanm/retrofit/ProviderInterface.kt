package com.cityoneprovider.retrofit

import com.cityoneprovider.retrofit.ApiConstant.LOGIN
import com.app.mndalakanm.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProviderInterface {
    @FormUrlEncoded
    @POST(LOGIN)
    fun login_signup(@FieldMap params: Map<String, String>): Call<SuccessLoginRes>

    @FormUrlEncoded
    @POST("verify_otp")
    fun verify_otp(@FieldMap params: Map<String, String>): Call<SuccessVerifyOtpRes>

    @FormUrlEncoded
    @POST("generate_pairing_code")
    fun generate_pairing_code(@FieldMap params: Map<String, String>): Call<SuccessPairingRes>

    @FormUrlEncoded
    @POST("pairing_code")
    fun pairing_code(@FieldMap params: Map<String, String>): Call<SuccessPairRes>

    @FormUrlEncoded
    @POST("get_user_profile")
    fun get_user_profile(@FieldMap params: Map<String, String>): Call<SuccessUserProfile>

    @FormUrlEncoded
    @POST("get_child_profile")
    fun get_child_profile(@FieldMap params: Map<String, String>): Call<SuccessPairRes>
    @FormUrlEncoded
    @POST("get_parent_child")
    fun get_parent_child(@FieldMap params: Map<String, String>): Call<SuccessChildsListRes>

    @Multipart
    @POST("add_child")
    fun add_child(
        @Part("child_id") register_id: RequestBody,
        @Part("age") loagen: RequestBody,
        @Part("name") name: RequestBody,
        @Part file1: MultipartBody.Part
    ): Call<SuccessAddChildRes>

    @Multipart
    @POST("update_user_profile")
    fun update_user_profile(

        @Part("user_id") user_id: RequestBody,
        @Part("name") name: RequestBody,
        @Part file1: MultipartBody.Part
    ): Call<SuccessUserProfile>


}