package com.meli.storage.wrapper.store

import com.meli.storage.wrapper.core.StorageKey

/**
 * Abstracción para un almacenamiento clave-valor asíncrono y genérico.
 *
 * Proporciona operaciones básicas para guardar, obtener, eliminar y limpiar
 * valores asociados a claves definidas mediante [StorageKey].
 *
 * La implementación concreta puede basarse en SharedPreferences, DataStore u otro sistema
 * de almacenamiento, pero debe respetar esta interfaz para facilitar el uso en la aplicación.
 *
 * @see StorageKey para detalles sobre la definición de las claves y su serialización.
 */
public interface KeyValueStore {

  /**
   * Almacena un valor [value] asociado a la clave [key].
   *
   * @param T Tipo del valor a almacenar.
   * @param key Clave que identifica el valor.
   * @param value Valor a almacenar.
   */
  suspend fun <T> put(key: StorageKey<T>, value: T)

  /**
   * Obtiene el valor asociado a la clave [key], o `null` si no existe.
   *
   * @param T Tipo esperado del valor.
   * @param key Clave para buscar el valor.
   * @return El valor almacenado o `null` si no está presente.
   */
  suspend fun <T> get(key: StorageKey<T>): T?

  /**
   * Elimina el valor asociado a la clave [key].
   *
   * @param key Clave cuyo valor será eliminado.
   */
  suspend fun remove(key: StorageKey<*>)

  /**
   * Elimina todos los valores almacenados.
   */
  suspend fun clear()
}
