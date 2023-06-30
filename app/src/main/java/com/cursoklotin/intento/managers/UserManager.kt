package com.cursoklotin.intento.managers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.cursoklotin.intento.UserData
import com.cursoklotin.intento.models.PersonaData

class UserManager private constructor(context: Context) {
    companion object {
        private const val PREF_NAME = "user_manager"
        private const val KEY_USER_ID = "user_id"

        private var instance: UserManager? = null

        fun getInstance(context: Context): UserManager {
            if (instance == null) {
                instance = UserManager(context.applicationContext)
            }
            return instance as UserManager
        }
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var userId: Int
        get() = sharedPreferences.getInt(KEY_USER_ID, -1)
        set(value) {
            sharedPreferences.edit().putInt(KEY_USER_ID, value).apply()
        }

    var personaData: PersonaData?
        get() {
            val idPersona = sharedPreferences.getInt("idPersona", -1)
            val nombres = sharedPreferences.getString("nombres", "") ?: ""
            val sexo = sharedPreferences.getString("sexo", "") ?: ""
            val telefono = sharedPreferences.getString("telefono", "") ?: ""
            val numeroCuenta = sharedPreferences.getString("numeroCuenta", "") ?: ""
            val banco = sharedPreferences.getString("banco", "") ?: ""
            val fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "") ?: ""
            val direccion = sharedPreferences.getString("direccion", "") ?: ""
            val distrito = sharedPreferences.getString("distrito", "") ?: ""
            val fechaCreacion = sharedPreferences.getString("fechaCreacion", "") ?: ""
            val ultimaActualizacion = sharedPreferences.getString("ultimaActualizacion", "") ?: ""

            return if (idPersona != -1) {
                PersonaData(
                    idPersona,
                    nombres,
                    sexo,
                    telefono,
                    numeroCuenta,
                    banco,
                    fechaNacimiento,
                    direccion,
                    distrito,
                    fechaCreacion,
                    ultimaActualizacion
                )
            } else {
                null
            }
        }
        set(value) {
            if (value != null) {
                sharedPreferences.edit().apply {
                    putInt("idPersona", value.idPersona)
                    putString("nombres", value.nombres)
                    putString("sexo", value.sexo)
                    putString("telefono", value.telefono)
                    putString("numeroCuenta", value.numeroCuenta)
                    putString("banco", value.banco)
                    putString("fechaNacimiento", value.fechaNacimiento)
                    putString("direccion", value.direccion)
                    putString("distrito", value.distrito)
                    putString("fechaCreacion", value.fechaCreacion)
                    putString("ultimaActualizacion", value.ultimaActualizacion)
                    apply()
                }
            } else {
                sharedPreferences.edit().clear().apply()
            }
        }

    fun isLoggedIn(): Boolean = userId != -1

    fun updatePersonaData(nuevosNombres: String, nuevoTelefono: String) {
        val personaData = personaData ?: return

        // Actualizar los campos de nombres y teléfono
        personaData.nombres = nuevosNombres
        personaData.telefono = nuevoTelefono

        // Guardar los nuevos datos de persona en el estado global
        this.personaData = personaData
    }

    fun resetPersonaData() {
        sharedPreferences.edit().clear().apply()
    }
}
