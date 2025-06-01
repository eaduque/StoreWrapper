package com.meli.storage.wrapper.store

import android.content.SharedPreferences
import androidx.core.content.edit
import com.meli.storage.wrapper.core.StorageKey
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * Implementación de [KeyValueStore] que utiliza SharedPreferences como almacenamiento.
 *
 * Esta clase permite guardar y recuperar datos de manera segura y estructurada usando
 * claves tipadas ([StorageKey]) y serialización JSON mediante [kotlinx.serialization].
 *
 * @param sharedPreferences Instancia de [SharedPreferences] utilizada como almacenamiento.
 * @param json Instancia de [Json] usada para serializar y deserializar los valores.
 *
 * ### Características
 * - Para tipos primitivos comunes (String, Int, Boolean, Float, Long), se usa directamente la API de SharedPreferences.
 * - Para cualquier otro tipo, el valor se serializa como JSON usando el serializer proporcionado en [StorageKey].
 * - La clave completa utilizada para guardar los datos es [StorageKey.fullName].
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * val key = keyOf(LoginStorage, LoginStorage.UserStorage)
 *
 * // Guarda un valor en SharedPreferences con base en la clave (key).
 * sharedPrefsKeyValueStore.put(key, userInfo)
 *
 * // Recupera un valor de SharedPreferences con base en la clave (key).
 * val storedUser = sharedPrefsKeyValueStore.get(key)
 * ```
 */
public class SharedPrefsKeyValueStore(
  private val sharedPreferences: SharedPreferences,
  private val json: Json = Json
) : KeyValueStore {

  /**
   * Guarda un valor bajo la clave especificada. Usa tipos nativos si el valor es primitivo,
   * o JSON si es un tipo complejo.
   */
  override suspend fun <T> put(key: StorageKey<T>, value: T) {
    sharedPreferences.edit {
      when (value) {
        is String -> putString(key.fullName, value)
        is Int -> putInt(key.fullName, value)
        is Boolean -> putBoolean(key.fullName, value)
        is Float -> putFloat(key.fullName, value)
        is Long -> putLong(key.fullName, value)
        else -> {
          val encoded = json.encodeToString(key.serializer, value)
          putString(key.fullName, encoded)
        }
      }
    }
  }

  /**
   * Recupera un valor desde SharedPreferences y lo deserializa. Devuelve `null` si la clave no existe
   * o si ocurre un error de deserialización.
   *
   * Usa tipos nativos si el tipo es primitivo; de lo contrario, intenta decodificar desde JSON.
   */
  @Suppress("UNCHECKED_CAST")
  override suspend fun <T> get(key: StorageKey<T>): T? {
    if (!sharedPreferences.contains(key.fullName)) return null

    return with(sharedPreferences) {
      when (key.serializer) {
        String.serializer() -> getString(key.fullName, null) as T?
        Int.serializer() -> getInt(key.fullName, 0) as T
        Boolean.serializer() -> getBoolean(key.fullName, false) as T
        Float.serializer() -> getFloat(key.fullName, 0f) as T
        Long.serializer() -> getLong(key.fullName, 0L) as T
        else -> {
          val jsonString = sharedPreferences.getString(key.fullName, null) ?: return null
          runCatching { json.decodeFromString(key.serializer, jsonString) }.getOrNull()
        }
      }
    }
  }

  /**
   * Elimina el valor asociado a la clave dada.
   */
  override suspend fun remove(key: StorageKey<*>) = sharedPreferences.edit { remove(key.fullName) }

  /**
   * Limpia todo el contenido de SharedPreferences.
   */
  override suspend fun clear() = sharedPreferences.edit { clear() }
}
