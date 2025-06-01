# StoreWrapper

Librería Android que funciona como un `wrapper` de las funcionalidades nativas de almacenamiento de datos en el dispositivo utilizando `SharedPreferences` y `DataStore`.

- Encapsula la funcionalidad de almacenamiento de datos en un solo objeto.
- Soporte de tipado seguro para almacenamiento, evitando errores de tipo al guardar y recuperar datos.
- Realmente fácil de usar. 
- Agrupa tus datos por frontends. 😎

## Content

- [Get Started](#instalación)
- [¿Cómo instanciar StorageManager?](#cómo-instanciar-storagemanager)
    - [Para DataStore (DS)](#para-datastore-ds)
    - [Para SharedPreferences (SP)](#para-sharedpreferences-sp)
- [Defnite un grupo lógico de datos](#defnite-un-grupo-lógico-de-datos)
- [Guardar un dato](#guardar-un-dato)
- [Leer un dato](#leer-un-dato)
- [Eliminar un dato](#eliminar-un-dato)
- [Sample](#sample)

## Instalación

```
implementation("com.meli.storage:wrapper:1.0.0") <-- En caso de publicación xD
```

## ¿Cómo instanciar StorageManager?

### Para DataStore (DS)

Obten la instancia de tu `DataStore`.

```kotlin
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "you_storage_name")
```

Ahora, crea la instancia de StorageManager.

```kotlin
val storageManager = StorageManager(DataStoreKeyValueStore(context.dataStore, json))
```

### Para SharedPreferences (SP)

Obten la instancia de tu `SharedPreferences`.

```kotlin
val sharedPrefs = context.getSharedPreferences("you_storage_name", Context.MODE_PRIVATE)
```

Ahora, crea la instancia de StorageManager.

```kotlin
val storageManager = StorageManager(SharedPrefsKeyValueStore(sharedPrefs, json))
```

## Defnite un grupo lógico de datos

Para guardar un dato usando `storageManager` debes definir un `StorageGroup` que es un **grupo lógico** que representa una pantalla o un módulo. Esto permite guardar los datos agrupados por fronted y evita posibles coliciones entre keys. Por ejemplo, deninamos la pantalla `Login` como un grupo lógico para nuestro ejemplo:

```kotlin
object LoginStorageGroup : StorageGroup {
  override val name = "login_screen"
}
```

Ahora que tenemos el `StorageGroup` creado podemos definir las keys que queremos guardar. Estas key se definen como objetos que implementan la interfaz `StorageName`.
Estos `StorageName`s reciben dos parámetros: El tipo de dato que se almacenará y el grupo al que pertenecen. Por ejemplo:

```kotlin
@Serializable
data class User(val name: String, val age: Int)

object UserStorage : StorageName<User, LoginStorageGroup> {
  override val name = "user_info" // Este es el nombre de la key para almacenar el dato
}
```
Puedes definir tantos `StorageName`s como necesites. ;)

## Guardar un dato

Para guardar un dato necesitarás crear un `StorageKey` con el `StorageGroup` y el `StorageName` que definiste...

```kotlin
val userKey = keyOf(LoginStorageGroup, UserStorage)
```
y luego puedes llamar al método `save()` de `storageManager` pasando la key y el dato a guardar.

```kotlin
suspend fun guardarDato() {
  storageManager.save(userKey, User("Alexis", 30))
}
```
En este punto, el usuario Alexis de edad 30 años ha sido guardado en **SharedPreferences** o **DataStore** (según el `storageManager` que denifiste).

## Leer un dato

Para leer un dato simplemente llama al método `read` de `storageManager` pasando la key del dato que quieres recuperar.

```kotlin
suspend fun leerDato() {   
  val user: User? = storageManager.read(userKey)
  // Hacer alguna operación con el user
}
```

## Eliminar un dato

Para eliminar un dato simplemente llama al método `delete` de `storageManager` pasando la key del dato que quieres eliminar.

```kotlin
suspend fun eliminarDato() {
  storageManager.delete(userKey)
}
```

## Sample
Puedes ver este [ejemplo](./sample) de una pequeña app que muestra el uso de `StoreWrapper` en acción.

### 🎉🎉🎉🎉🎉🎉
****
