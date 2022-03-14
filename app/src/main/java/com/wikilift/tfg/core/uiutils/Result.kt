package com.wikilift.tfg.core.uiutils

sealed class Result<out T> {
    class Loading<out T>: Result<T>()
    data class Succes<out T>(val data:T): Result<T>()
    data class Failure(val exception: Exception): Result<Nothing>()

}