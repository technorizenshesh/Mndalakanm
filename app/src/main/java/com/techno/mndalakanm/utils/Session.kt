package com.techno.mndalakanm.utils

import android.content.Context
import android.content.SharedPreferences

class Session(private val _context: Context) : Any() {
    private val Rapidine_pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        Rapidine_pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = Rapidine_pref.edit()
        editor.apply()
    }

    val mobile: String?
        get() = Rapidine_pref.getString(Mobile, "")
    var user_name: String?
        get() = Rapidine_pref.getString(User_name, "")
        set(user_name) {
            editor.putString(User_name, user_name)
            editor.apply()
        }
    var restraID: String?
        get() = Rapidine_pref.getString(RestraID, "")
        set(restra) {
            editor.putString(RestraID, restra)
            editor.apply()
        }
    var publishfile: String?
        get() = Rapidine_pref.getString(Publishfile, "")
        set(user_name) {
            editor.putString(Publishfile, user_name)
            editor.apply()
        }
    var publishType: String?
        get() = Rapidine_pref.getString(PublishType, "")
        set(user_name) {
            editor.putString(PublishType, user_name)
            editor.apply()
        }
    var publishTxt: String?
        get() = Rapidine_pref.getString(PublishTxt, "")
        set(user_name) {
            editor.putString(PublishTxt, user_name)
            editor.apply()
        }
    var chatName: String?
        get() = Rapidine_pref.getString(ChatName, "")
        set(user_name) {
            editor.putString(ChatName, user_name)
            editor.apply()
        }
    var chatImage: String?
        get() = Rapidine_pref.getString(ChatImage, "")
        set(user_name) {
            editor.putString(ChatImage, user_name)
            editor.apply()
        }

    fun get_FormStatus(): String? {
        return Rapidine_pref.getString(FormStatus, "")
    }

    fun set_FormStatus(fm: String?) {
        editor.putString(FormStatus, fm)
        editor.apply()
        editor.commit()
    }

    var userId: String?
        get() = Rapidine_pref.getString(UserId, "")
        set(userId) {
            editor.putString(UserId, userId)
            editor.apply()
        }
    var hOME_LAT: String?
        get() = Rapidine_pref.getString(HOME_LAT, "")
        set(userId) {
            editor.putString(HOME_LAT, userId)
            editor.apply()
        }
    var hOME_LONG: String?
        get() = Rapidine_pref.getString(HOME_LONG, "")
        set(userId) {
            editor.putString(HOME_LONG, userId)
            editor.apply()
        }

    fun logout() {
        editor.clear()
        editor.apply()
        /* Intent showLogin = new Intent(_context, LoginAct.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(showLogin);*/
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn)
        editor.commit()
    }

    val isLoggedIn: Boolean
        get() = Rapidine_pref.getBoolean(IS_LOGGEDIN, false)
    var fireBaseToken: String?
        get() = Rapidine_pref.getString(FireBaseToken, "")
        set(fcmid) {
            editor.putString(FireBaseToken, fcmid)
            editor.apply()
        }

    companion object {
        private val TAG = Session::class.java.simpleName
        private const val PREF_NAME = "Rapidine_pref2"
        private const val IS_LOGGEDIN = "isLoggedIn"
        private const val FormStatus = "formstatus"
        private const val Mobile = "mobile"
        private const val UserId = "user_id"
        private const val User_name = "user_name"
        private const val FireBaseToken = "fcmid"
        private const val HOME_LAT = "home_lat"
        private const val HOME_LONG = "home_long"
        private const val ChatName = "chat_name"
        private const val ChatImage = "chat_image"
        private const val PublishTxt = "publish_txt"
        private const val PublishType = "publish_type"
        private const val Publishfile = "publish_file"
        private const val RestraID = "restra_id"
    }
}