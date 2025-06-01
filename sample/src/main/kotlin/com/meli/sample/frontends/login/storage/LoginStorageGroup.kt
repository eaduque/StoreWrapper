package com.meli.sample.frontends.login.storage

import com.meli.sample.models.UserInfo
import com.meli.storage.wrapper.core.StorageGroup
import com.meli.storage.wrapper.core.StorageName

/**
 * Representa el grupo de almacenamiento (frontend) asociado a la pantalla de login.
 *
 * Esta clase define un [StorageGroup] con el nombre `"login_screen"` que agrupa todas las claves
 * (keys) de almacenamiento relacionadas con este flujo o pantalla. Sirve como un espacio de nombres lógico
 * para organizar y acceder de forma estructurada a valores persistentes usando la librería de almacenamiento.
 *
 * Cada clave se define como un objeto que implementa la interfaz [StorageName], indicando:
 * - El tipo de dato que se almacenará.
 * - El grupo ([StorageGroup]) al que pertenece, en este caso `LoginStorageGroup`.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * val userKey = keyOf(LoginStorageGroup, LoginStorageGroup.UserStorage)
 * spStoreManager.save(key = userKey, value = userInfo)
 * ```
 *
 * @see StorageGroup
 * @see StorageName
 */
object LoginStorageGroup : StorageGroup {

  override val name = "login_screen"

  object UserStorage : StorageName<UserInfo, LoginStorageGroup> {
    override val name = "user_info"
  }

  object TokenStorage : StorageName<String, LoginStorageGroup> {
    override val name = "auth_token"
  }

  object CounterStorage : StorageName<Int, LoginStorageGroup> {
    override val name = "counter"
  }
}
