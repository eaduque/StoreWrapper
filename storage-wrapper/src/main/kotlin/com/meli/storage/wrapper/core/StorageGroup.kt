package com.meli.storage.wrapper.core

/**
 * Representa un grupo lógico (frontend) para organizar las claves de almacenamiento.
 *
 * Cada grupo suele corresponder a una pantalla o módulo en la aplicación,
 * permitiendo separar y evitar colisiones entre claves con el mismo nombre.
 *
 * Para definir un grupo, basta con implementar esta interfaz y proporcionar un nombre único.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```
 * object LoginScreen : StorageGroup {
 *     override val name = "login_screen"
 * }
 * ```
 *
 * Las claves de almacenamiento ([StorageKey]) estarán asociadas a un grupo,
 * lo que facilita la organización y gestión de los datos almacenados.
 */
public interface StorageGroup {

  /**
   * Nombre único que identifica el grupo (fronted).
   */
  val name: String
}
