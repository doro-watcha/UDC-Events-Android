package com.goddoro.common.data.api.response


enum class ResponseCode(val code: Int) {

    UNKNOWN(-999),

    REQUEST_BODY_VALIATION_FAIL(102),

    UNVERIFIED_EMAIL(202),

    EXIST_EMAIL(500),
    SERVER_ERROR(700),

    NOT_FOUND(404),
    ENTRY_DELETED(601),

}