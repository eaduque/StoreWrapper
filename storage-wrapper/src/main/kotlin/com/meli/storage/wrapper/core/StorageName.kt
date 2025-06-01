package com.meli.storage.wrapper.core

/**
 * Representa el nombre de una clave de almacenamiento asociada a un grupo lógico (`StorageGroup`)
 * y a un tipo de dato específico.
 *
 * Esta interfaz se utiliza para definir claves fuertemente tipadas que serán utilizadas por el sistema
 * de almacenamiento (SharedPreferences o DataStore).
 *
 * Debe implementarse para cada clave que se desea utilizar dentro de un grupo de almacenamiento (frontend).
 *
 * @param T El tipo de dato que se desea almacenar (por ejemplo, String, Int, o un objeto serializable).
 * @param G El grupo lógico (frontend) al que pertenece esta clave. Este grupo debe implementar la interfaz [StorageGroup].
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```
 * object LoginStorage : StorageGroup {
 *     override val name = "login_screen"
 *
 *     // Se define el tipo de dato (String)
 *     // y el frontend (Login) al que pertenece.
 *     object TokenStorage : StorageName<String, LoginStorage> {
 *         override val name = "auth_token"
 *     }
 * }
 * ```
 */
public interface StorageName<T, G : StorageGroup> {

  /**
   * Nombre único de la clave dentro del grupo.
   */
  val name: String
}
