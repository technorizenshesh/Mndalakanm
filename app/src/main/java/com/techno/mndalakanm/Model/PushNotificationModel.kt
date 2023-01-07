package com.techno.mndalakanm.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PushNotificationModel : Serializable {
    @SerializedName("result")
    @Expose
    var result: String? = null

    @SerializedName("key")
    @Expose
    var key: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("userid")
    @Expose
    var userid: String? = null

    @SerializedName("orderid")
    @Expose
    var orderid: String? = null

    @SerializedName("job_id")
    @Expose
    var job_id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("userimage")
    @Expose
    var userimage: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null
}