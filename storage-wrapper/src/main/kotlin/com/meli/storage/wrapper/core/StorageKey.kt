package com.meli.storage.wrapper.core

import kotlinx.serialization.KSerializer

/**
 * Representa una clave única para almacenar y recuperar un valor de tipo [T]
 * en un grupo lógico (frontend) específico dentro del sistema de almacenamiento.
 *
 * La clave combina el nombre del grupo y el nombre de la clave para evitar
 * colisiones y facilitar la organización de datos.
 *
 * Además, contiene el [serializer] necesario para (de)serializar el valor almacenado,
 * lo que permite trabajar con tipos complejos de forma segura.
 *
 * ### Diferencias con [StorageName]
 * - `StorageName` es una interfaz que define el nombre de la clave y la relación con el grupo,
 * pero no contiene información sobre el tipo ni el serializer.
 * - `StorageKey` es una clase concreta que añade el serializer y representa la clave completa
 * lista para ser usada para almacenamiento o lectura de datos.
 *
 * @param T El tipo de dato que se almacena o recupera con esta clave.
 * @property group Nombre del grupo lógico (lo que ustedes llaman frontend. Ej: Una pantalla o un módulo).
 * @property name Nombre único de la clave dentro del grupo.
 * @property serializer Serializador de Kotlinx.serialization para el tipo [T].
 *
 * @property fullName Nombre completo que combina el grupo y la clave, con formato `grupo::clave`.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```
 * val userKey = StorageKey(
 *   group = "login_screen",
 *   name = "user_info",
 *   serializer = UserInfo.serializer()
 * )
 *
 * val keyFullName = userKey.fullName // "login_screen::user_info"
 * ```
 */
public data class StorageKey<T>(
  val group: String,
  val name: String,
  val serializer: KSerializer<T>
) {

  /**
   * Nombre completo usado internamente para evitar colisiones.
   */
  val fullName: String get() = "$group::$name"
}
