package com.meli.storage.wrapper.core

import kotlinx.serialization.serializer

/**
 * Crea una instancia de [StorageKey] a partir de un [StorageGroup] y un [StorageName].
 *
 * Esta función sirve como [DSL](https://medium.com/@electrophile172/understanding-kotlin-dsls-a-deep-dive-57818bf79a50)
 * para generar claves de almacenamiento fuertemente tipadas,
 * combinando el nombre del grupo y el nombre de la clave, y asociando automáticamente el
 * serializador correspondiente al tipo `T`.
 *
 * @param group El grupo lógico al que pertenece la clave (por ejemplo, una pantalla o módulo).
 * @param storageName La clave específica dentro del grupo, con su tipo y nombre definidos.
 * @return Una instancia de [StorageKey] que puede ser utilizada con el [StorageManager].
 *
 * Esta función es `inline` y utiliza `reified` para inferir automáticamente el tipo `T` en tiempo de compilación,
 * lo cual permite obtener el `KSerializer<T>` necesario para serializar y deserializar los valores.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * val userKey = keyOf(LoginStorage, LoginStorage.UserStorage)
 * storageManager.save(userKey, userInfo)
 * ```
 */
public inline fun <reified T, G : StorageGroup> keyOf(
  group: G,
  storageName: StorageName<T, G>,
): StorageKey<T> = StorageKey(group.name, storageName.name, serializer())
