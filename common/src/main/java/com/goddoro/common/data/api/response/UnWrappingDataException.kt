package com.goddoro.common.data.api.response

class UnWrappingDataException(val errorCode: Int, message: String) : Exception(message)
