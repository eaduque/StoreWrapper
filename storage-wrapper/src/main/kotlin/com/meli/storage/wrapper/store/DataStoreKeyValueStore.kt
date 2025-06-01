package com.meli.storage.wrapper.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.meli.storage.wrapper.core.StorageKey
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

/**
 * Implementación de [KeyValueStore] que utiliza DataStore como almacenamiento.
 *
 * Esta clase permite guardar y recuperar datos de manera segura y estructurada usando
 * claves tipadas ([StorageKey]) y serialización JSON mediante [kotlinx.serialization].
 *
 * @param dataStore Instancia de [DataStore] de tipo [Preferences] utilizada como almacenamiento.
 * @param json Instancia de [Json] usada para serializar y deserializar los valores.
 *
 * ### Características
 * - Todos los valores, incluidos los tipos primitivos, se serializan a JSON antes de almacenarse.
 * - Las claves se construyen usando [StorageKey.fullName], lo que permite agrupación lógica de grupos (frontends).
 * - Maneja automáticamente la codificación y decodificación de tipos complejos.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * val key = keyOf(LoginStorage, LoginStorage.UserStorage)
 *
 * // Guarda un valor en DataStore con base en la clave (key).
 * dataStoreKeyValueStore.put(key, userInfo)
 *
 * // Recupera un valor de DataStore con base en la clave (key).
 * val storedUser = dataStoreKeyValueStore.get(key)
 * ```
 */
public class DataStoreKeyValueStore(
  private val dataStore: DataStore<Preferences>,
  private val json: Json = Json
) : KeyValueStore {

  /**
   * Guarda un valor serializado en DataStore bajo la clave proporcionada.
   */
  override suspend fun <T> put(key: StorageKey<T>, value: T) {
    val encoded = json.encodeToString(key.serializer, value)
    dataStore.edit { it[stringPreferencesKey(key.fullName)] = encoded }
  }

  /**
   * Recupera un valor desde DataStore y lo deserializa. Devuelve `null` si la clave no existe
   * o si ocurre un error de deserialización.
   */
  override suspend fun <T> get(key: StorageKey<T>): T? {
    val prefs = dataStore.data.first()
    val stored = prefs[stringPreferencesKey(key.fullName)] ?: return null
    return runCatching { json.decodeFromString(key.serializer, stored) }.getOrNull()
  }

  /**
   * Elimina el valor asociado a la clave dada.
   */
  override suspend fun remove(key: StorageKey<*>) {
    dataStore.edit { it.remove(stringPreferencesKey(key.fullName)) }
  }

  /**
   * Limpia todo el contenido de DataStore.
   */
  override suspend fun clear() {
    dataStore.edit { it.clear() }
  }
}
