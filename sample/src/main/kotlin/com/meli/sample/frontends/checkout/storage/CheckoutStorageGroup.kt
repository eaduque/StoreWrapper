package com.meli.sample.frontends.checkout.storage

import com.meli.sample.models.CheckoutInfo
import com.meli.storage.wrapper.core.StorageGroup
import com.meli.storage.wrapper.core.StorageName

/**
 * Representa el grupo de almacenamiento (frontend) asociado a la pantalla de checkout.
 *
 * Esta clase define un [StorageGroup] con el nombre `"checkout_screen"` que agrupa todas las claves
 * (keys) de almacenamiento relacionadas con este flujo o pantalla. Sirve como un espacio de nombres lógico
 * para organizar y acceder de forma estructurada a valores persistentes usando la librería de almacenamiento.
 *
 * Cada clave se define como un objeto que implementa la interfaz [StorageName], indicando:
 * - El tipo de dato que se almacenará.
 * - El grupo ([StorageGroup]) al que pertenece, en este caso `CheckoutStorageGroup`.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * val addressKey = keyOf(CheckoutStorageGroup, CheckoutStorageGroup.AddressStorage)
 * spStoreManager.save(key = addressKey, value = addressInfo)
 * ```
 *
 * @see StorageGroup
 * @see StorageName
 */
object CheckoutStorageGroup : StorageGroup {

  override val name = "checkout_screen"

  object AddressStorage : StorageName<CheckoutInfo, CheckoutStorageGroup> {
    override val name = "address_info"
  }

  object TokenStorage : StorageName<String, CheckoutStorageGroup> {
    override val name = "auth_token"
  }

  object CounterStorage : StorageName<Int, CheckoutStorageGroup> {
    override val name = "counter"
  }
}
