package com.vilborgtower.user.utils

object Config {
    // global topic to receive app wide push notifications
    const val TOPIC_GLOBAL = "global"

    // broadcast receiver intent filters
    const val REGISTRATION_COMPLETE = "registrationComplete"
    const val PUSH_NOTIFICATION = "pushNotification"

    // id to handle the notification in the notification tray
    const val NOTIFICATION_ID = 100
    const val NOTIFICATION_ID_BIG_IMAGE = 101
    const val GET_DATA_NOTIFICATION = "getDataPushNotification"
}