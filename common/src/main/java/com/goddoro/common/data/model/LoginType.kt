package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.*
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type


/**
 * created By DORO 2020/08/16
 */

@JsonAdapter(LoginTypeSerializer::class)
@Parcelize
enum class LoginType(val value: String) : Parcelable {
    UNKNOWN(""),

    EMAIL("email"),
    KAKAO("kakao"),
    GOOGLE("google"),
    FACEBOOK("facebook")

    ;

    companion object {
        fun parse(value: String) = LoginType.values().firstOrNull { value == it.value } ?: UNKNOWN
    }
}

class LoginTypeSerializer : JsonSerializer<LoginType>, JsonDeserializer<LoginType> {
    override fun serialize(src: LoginType?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.value ?: LoginType.UNKNOWN.value)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LoginType {
        return LoginType.parse(json?.asString ?: LoginType.UNKNOWN.value)
    }
}