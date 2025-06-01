package com.meli.storage.wrapper.core

import com.meli.storage.wrapper.store.KeyValueStore

/**
 * Wrapper de alto nivel para operaciones de almacenamiento clave-valor.
 *
 * Esta clase actúa como fachada o wrapper sobre una implementación de [KeyValueStore],
 * proporcionando una API sencilla para guardar, leer y eliminar valores.
 *
 * Puede usarse indistintamente con distintas implementaciones (como SharedPreferences o DataStore),
 * lo que facilita cambiar el backend sin alterar el código cliente.
 *
 * @param keyValueStore Implementación concreta de almacenamiento.
 *
 * @see StorageKey para la definición de claves tipo-seguras.
 */
public class StorageManager(
  private val keyValueStore: KeyValueStore
) {

  /**
   * Guarda un valor [value] asociado a la clave [key].
   *
   * @param T Tipo del valor a guardar.
   * @param key Clave con la que se almacenará el valor.
   * @param value Valor a almacenar.
   */
  suspend fun <T> save(key: StorageKey<T>, value: T) = keyValueStore.put(key, value)

  /**
   * Lee el valor asociado a la clave [key].
   *
   * @param T Tipo esperado del valor.
   * @param key Clave que identifica el valor.
   * @return El valor almacenado o `null` si no existe.
   */
  suspend fun <T> read(key: StorageKey<T>): T? = keyValueStore.get(key)

  /**
   * Elimina el valor asociado a la clave proporcionada, sin requerir conocer su tipo exacto.
   *
   * @param key La clave de almacenamiento del valor a eliminar. El tipo genérico del valor (`T`) no es necesario
   *            para esta operación, por lo tanto se utiliza `StorageKey<*>` para indicar que no se usará.
   *
   * Este método no necesita conocer el tipo del dato almacenado, ya que no se realiza ni serialización ni
   * deserialización del valor: solo se elimina del almacenamiento.
   *
   * Para operaciones que requieren el tipo (como guardar o leer), se usa `StorageKey<T>`.
   */
  suspend fun delete(key: StorageKey<*>) = keyValueStore.remove(key)

  /**
   * Elimina todos los valores almacenados.
   */
  suspend fun clearAll() = keyValueStore.clear()
}
