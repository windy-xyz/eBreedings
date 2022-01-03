package com.wcs.ebreedings.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val useridError: Int? = null,
    val passwordError: Int? = null,
    val estateError: Int? = null,
    val isDataValid: Boolean = false
)