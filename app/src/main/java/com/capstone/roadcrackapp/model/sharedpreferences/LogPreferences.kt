package com.capstone.roadcrackapp.model.sharedpreferences

import android.content.Context

class LogPreferences(context: Context) {
    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setToken(token:String){
        val edit = preference.edit()
        edit.putString(TOKEN,token)
        edit.apply()
    }

    fun getToken(): String? {
        return preference.getString(TOKEN, null)
    }

    fun clearToken(){
        val edit = preference.edit().clear()
        edit.apply()
    }

    companion object{
        const val PREF_NAME = "prefname"
        const val TOKEN = "token"
    }
}