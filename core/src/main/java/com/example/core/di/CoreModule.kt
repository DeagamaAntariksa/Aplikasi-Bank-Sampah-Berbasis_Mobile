package com.example.core.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.core.data.source.TrashRepository
import com.example.core.data.source.UserRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.SessionPreference
import com.example.core.data.source.remote.ITrashDatasource
import com.example.core.data.source.remote.IUserDatasource
import com.example.core.data.source.remote.datasource.TrashFirebaseDatasource
import com.example.core.data.source.remote.datasource.UserFirebaseDatasource
import com.example.core.domain.repository.ITrashRepository
import com.example.core.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single { getSharedPrefs(androidContext()) }
    single { SessionPreference(get()) }
}

fun getSharedPrefs(context: Context): SharedPreferences {
    val prefs = "session_prefs"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()

            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()

            EncryptedSharedPreferences
                .create(
                    context,
                    prefs,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
        } catch (e: Exception) {
            context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        }
    } else {
        context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
    }
}

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance().reference }
}

val dataSourceModule = module {
    single<IUserDatasource> { UserFirebaseDatasource(get(), get()) }
    single<ITrashDatasource> { TrashFirebaseDatasource(get()) }
    single { LocalDataSource(get()) }
}

val repositoryModule = module {
    single<IUserRepository> { UserRepository(get(), get()) }
    single<ITrashRepository> { TrashRepository(get()) }
}