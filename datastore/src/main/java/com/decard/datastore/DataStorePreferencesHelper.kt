package com.decard.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStorePreferencesHelper private constructor(var storeName: String? = null) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = storeName
        ?: "MiniGamePreferencesDataStore")

    companion object {
        @Volatile
        private var dataStorePreferencesHelper: DataStorePreferencesHelper? = null

        fun getInstance(storeName: String? = null): DataStorePreferencesHelper {
            if (dataStorePreferencesHelper == null) {
                synchronized(DataStorePreferencesHelper::class.java) {
                    if (dataStorePreferencesHelper == null) {
                        dataStorePreferencesHelper = DataStorePreferencesHelper(storeName)
                    }
                }
            }
            return dataStorePreferencesHelper!!
        }
    }

    suspend fun saveIntValue(context: Context, keyName: String, value: Int) {
        val key = intPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getIntValue(context: Context, keyName: String, defaultValue: Int? = null): Int {
        val key = intPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: 0)
        }.first()
    }

    suspend fun saveLongValue(context: Context, keyName: String, value: Long) {
        val key = longPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getLongValue(context: Context, keyName: String, defaultValue: Long? = null): Long {
        val key = longPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: 0L)
        }.first()
    }

    suspend fun saveFloatValue(context: Context, keyName: String, value: Float) {
        val key = floatPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getFloatValue(context: Context, keyName: String, defaultValue: Float? = null): Float {
        val key = floatPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: 0f)
        }.first()
    }

    suspend fun saveDoubleValue(context: Context, keyName: String, value: Double) {
        val key = doublePreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getDoubleValue(context: Context, keyName: String, defaultValue: Double? = null): Double {
        val key = doublePreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: 0.0)
        }.first()
    }

    suspend fun saveBooleanValue(context: Context, keyName: String, value: Boolean) {
        val key = booleanPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getBooleanValue(context: Context, keyName: String, defaultValue: Boolean? = null): Boolean {
        val key = booleanPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: false)
        }.first()
    }

    suspend fun saveStringValue(context: Context, keyName: String, value: String) {
        val key = stringPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getStringValue(context: Context, keyName: String, defaultValue: String? = null): String {
        val key = stringPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: "")
        }.first()
    }

    suspend fun saveStringSetValue(context: Context, keyName: String, value: Set<String>) {
        val key = stringSetPreferencesKey(keyName)
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getStringSetValue(context: Context, keyName: String, defaultValue: Set<String>? = null): Set<String> {
        val key = stringSetPreferencesKey(keyName)
        return context.dataStore.data.map {
            it[key] ?: (defaultValue ?: setOf())
        }.first()
    }

}