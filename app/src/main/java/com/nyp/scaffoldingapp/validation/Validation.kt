package com.nyp.scaffoldingapp.validation

import android.util.Patterns

object Validators {
    const val MIN_PASSWORD_LENGTH = 6

    // Returns an error message, or null if the input is fine
    fun emailError(email: String): String? = when {
        email.isBlank() -> "Email is required"
        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Enter a valid email"
        else -> null
    }

    fun passwordError(password: String): String? = when {
        password.isEmpty() -> "Password is required"
        password.length < MIN_PASSWORD_LENGTH ->
            "Password must be at least $MIN_PASSWORD_LENGTH characters"
        else -> null
    }

    const val MIN_NAME_LENGTH = 2

    fun nameError(name: String): String? = when {
        name.isBlank() -> "Name is required"
        name.trim().length < MIN_NAME_LENGTH -> "Name must be at least $MIN_NAME_LENGTH characters"
        else -> null
    }

    fun confirmPasswordError(password: String, confirm: String): String? = when {
        confirm.isEmpty() -> "Confirm your password"
        confirm != password -> "Passwords don't match"
        else -> null
    }
}