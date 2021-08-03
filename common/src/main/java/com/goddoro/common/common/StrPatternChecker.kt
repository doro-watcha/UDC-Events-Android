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

    private val youtube_url_pattern =".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*"
    private val urlPatternMatcher = Pattern.compile(youtube_url_pattern)

    fun YoutubeUrlTypeOk ( url : String) : Boolean {
        return urlPatternMatcher.matcher(url).matches()
    }

    fun getYoutubeIdFromUrl ( url : String) : String? {
        debugE("UploadClassBasicFragment", urlPatternMatcher.matcher(url))
        return if (urlPatternMatcher.matcher(url).find()) urlPatternMatcher.matcher(url).group(1)
        else ""
    }

    const val youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
    val videoIdRegex = arrayOf(
        "\\?vi?=([^&]*)",
        "watch\\?.*v=([^&]*)",
        "(?:embed|vi?)/([^/?]*)",
        "^([A-Za-z0-9\\-]*)"
    )

    fun extractVideoIdFromUrl(url: String): String? {
        val youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url)
        for (regex in videoIdRegex) {
            val compiledPattern = Pattern.compile(regex)
            val matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain)
            if (matcher.find()) {
                return matcher.group(1)
            }
        }
        return null
    }

    private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
        val compiledPattern = Pattern.compile(youTubeUrlRegEx)
        val matcher = compiledPattern.matcher(url)
        return if (matcher.find()) {
            url.replace(matcher.group(), "")
        } else url
    }

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
