import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("contador_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val CONTADOR_KEY = intPreferencesKey("contador")
        val DATA_KEY = stringPreferencesKey("data_ultimo_clique")
    }

    val contador: Flow<Int> = context.dataStore.data.map { it[CONTADOR_KEY] ?: 0 }
    val dataUltimoClique: Flow<String> = context.dataStore.data.map { it[DATA_KEY] ?: "Nunca" }

    suspend fun salvarContador(valor: Int) {
        context.dataStore.edit { prefs ->
            prefs[CONTADOR_KEY] = valor
        }
    }

    suspend fun salvarDataHora(data: String) {
        context.dataStore.edit { prefs ->
            prefs[DATA_KEY] = data
        }
    }
}
