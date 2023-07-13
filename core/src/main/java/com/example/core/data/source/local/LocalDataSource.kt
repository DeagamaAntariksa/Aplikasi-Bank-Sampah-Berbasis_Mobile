package com.example.core.data.source.local

import android.util.Log
import com.example.core.domain.model.Session

class LocalDataSource(private val prefs: SessionPreference) {

    fun saveSession(session: Session): Boolean =
        try {
            prefs.saveSession(session)
            true
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            false
        }

    fun getSession(): Session? =
        try {
            prefs.getSession()
        } catch (e: Exception) {
            Log.e("LocalDataSource", e.message.toString())
            null
        }

    fun clearSession(): Boolean =
        try {
            prefs.clearSession()
            true
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            false
        }
}