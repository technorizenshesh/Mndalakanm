package com.app.mndalakanm.Model

import com.google.gson.annotations.SerializedName

data class SuccessAddChildRes (

    @SerializedName("result"  ) var result  : AddChildRes? = AddChildRes(),
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : String? = null

)

data class  AddChildRes (
    @SerializedName("id"           ) var id          : String? = null,
    @SerializedName("parent_id"    ) var parentId    : String? = null,
    @SerializedName("pairing_code" ) var pairingCode : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("image"        ) var image       : String? = null,
    @SerializedName("age"          ) var age         : String? = null,
    @SerializedName("register_id"  ) var registerId  : String? = null,
    @SerializedName("mobile_id"    ) var mobileId    : String? = null,
    @SerializedName("lat"          ) var lat         : String? = null,
    @SerializedName("lon"          ) var lon         : String? = null,
    @SerializedName("status"       ) var status      : String? = null,
    @SerializedName("date_time"    ) var dateTime    : String? = null

)