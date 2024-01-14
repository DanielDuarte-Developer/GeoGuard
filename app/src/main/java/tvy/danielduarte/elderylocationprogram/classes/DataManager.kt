package tvy.danielduarte.elderylocationprogram.classes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import tvy.danielduarte.elderylocationprogram.ProfileObj

class DataManager(private val context: Context) {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    suspend fun write (key: Int, value: ProfileObj){
        val dataStoreKey = stringPreferencesKey(key.toString())
        var valueJson = Gson().toJson(value)
        this.context.dataStore.edit {
                settings -> settings[dataStoreKey] = valueJson
        }
    }

    suspend fun read(key: Int): ProfileObj? {
        val dataStoreKey = stringPreferencesKey(key.toString())
        val preferences = this.context.dataStore.data.first()

        return preferences[dataStoreKey]?.let { jsonString ->
            Gson().fromJson(jsonString, ProfileObj::class.java)
        }
    }

    suspend fun size(): Int{
        var count = 0

        this.context.dataStore.data.collect { preferences ->
            count++
        }

        return count
    }

}