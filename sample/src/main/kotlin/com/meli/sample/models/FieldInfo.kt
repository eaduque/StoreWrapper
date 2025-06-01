package com.meli.sample.models

/**
 * Representa el estado de un campo individual dentro del UI state.
 *
 * @param T El tipo de dato que contiene el campo (por ejemplo, String, Int, Boolean, etc.).
 * @property value El valor actual del campo. Puede ser `null` si aún no ha sido asignado.
 * @property modified Indica si el campo ha sido modificado por el usuario.
 *
 * Esta clase es útil para manejar formularios o entradas de usuario en Jetpack Compose,
 * permitiendo distinguir entre un campo vacío y uno que aún no ha sido tocado.
 *
 * ㅤ
 *
 * ### Ejemplo:
 * ```kotlin
 * data class UiState(
 *   val name: FieldInfo<String> = FieldInfo(),
 *   val age: FieldInfo<Int> = FieldInfo()
 * )
 * ```
 */
data class FieldInfo<T>(val value: T? = null, val modified: Boolean = false)
