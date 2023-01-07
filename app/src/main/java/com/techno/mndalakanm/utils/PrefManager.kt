package com.vilborgtower.user.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PrefManager(private val context: Context) {
    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        fun setString(name: String?, value: String?) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(name, value)
            editor.apply()
        }

        fun getString(name: String?): String? {
            return sharedPreferences.getString(name, "")
        }

        fun setInt(name: String?, value: Int) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(name, value)
            editor.apply()
        }

        fun getInt(name: String?): Int {
            return sharedPreferences.getInt(name, 0)
        }

        fun setBoolean(name: String?, value: Boolean) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(name, value)
            editor.apply()
        }

        fun getBoolean(name: String?): Boolean {
            return sharedPreferences.getBoolean(name, false)
        }

        fun logout() {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }

        fun setPreferenceObject(key: String?, modal: Any?) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val jsonObject: String = gson.toJson(modal)
            editor.putString(key, jsonObject)
            editor.apply()
        }
        //    public static UserModel getPreferenceObjectJson(String key) {
        //        String json = sharedPreferences.getString(key, "");
        //        Gson gson = new Gson();
        //        UserModel userModel = gson.fromJson(json, UserModel.class);
        //        return userModel;
        //    }
    }

    init {
        sharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}