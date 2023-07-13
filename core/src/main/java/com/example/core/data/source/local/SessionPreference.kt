package com.example.core.data.source.local

import android.content.SharedPreferences
import com.example.core.domain.model.Session

class SessionPreference (private val sp: SharedPreferences) {

    fun saveSession(session: Session) {
        sp.edit()
            .putString(TAG_USER_ID, session.id)
            .putString(TAG_USER_NAME, session.username)
            .putString(TAG_ROLE, session.role)
            .apply()
    }

    fun getSession(): Session = Session(
        id = sp.getString(TAG_USER_ID, "") ?: "",
        username = sp.getString(TAG_USER_NAME, "") ?: "",
        role = sp.getString(TAG_ROLE, "") ?: ""
    )

    fun clearSession() {
        sp.edit().clear().apply()
    }

    companion object {
        const val TAG_USER_ID = "USER_ID"
        const val TAG_USER_NAME = "USER_NAME"
        const val TAG_USER_FULLNAME = "USER_FULLNAME"
        const val TAG_ROLE = "ROLE"
    }
}