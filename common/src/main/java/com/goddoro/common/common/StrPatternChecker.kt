package com.goddoro.common.common

import java.util.regex.Pattern


/**
 * created By DORO 2020/09/12
 */

object StrPatternChecker {
    private const val str_email_pattern = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$"
    private val emailMatcher = Pattern.compile(str_email_pattern)
    private const val str_password_pattern =
        "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*]{8,20}$"
    private val PasswordMatcher = Pattern.compile(str_password_pattern)
    private const val str_login_id_pattern = "^[a-zA-Z0-9.]{6,24}$"
    private val LoginIdMattcher = Pattern.compile(str_login_id_pattern)

    //    private static final String str_user_name_pattern = "^(?=.*[a-zA-Z0-9.]){6,24}([a-zA-Z0-9]+[.]{0,1}[a-zA-Z0-9]+)$";
    private const val str_user_name_pattern = "^(?=.{2,24}$)([a-zA-Z0-9가-]+[.]{0,1}[a-zA-Z0-9]+)$"
    private val UserNameMatcher = Pattern.compile(str_user_name_pattern)
    fun EmailTypeOk(email: String?): Boolean {
        return emailMatcher.matcher(email).matches()
    }

    fun PwdTypeOk(password: String?): Boolean {
        return PasswordMatcher.matcher(password).matches()
    }

    fun LoginIdTypeOk(_contents: String?): Boolean {
        return LoginIdMattcher.matcher(_contents).matches()
    }

    fun UserNameTypeOk(_contents: String?): Boolean {
        return UserNameMatcher.matcher(_contents).matches()
    }

    fun Email_Pattern(): Pattern {
        return emailMatcher
    }
}
