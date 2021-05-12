package com.goddoro.common

object CommonConst {

    enum class eventType { 배틀, 퍼포먼스, Workshop, 파티}


    const val UPDATE_FCM_TOKEN = "update_fcm_token"
    const val UDC_FCM_TOKEN = "udc_fcm_token"

    const val VIDEO_PAGE_LIMIT = 10

    const val PUSH_NOTI_TYPE = "push_type"
    const val PUSH_NOTI_TARGET = "push_targetId"
    const val PUSH_NOTI_TARGET_PART = "push_target_part"

    enum class NotiType { WOW, COMMENT, CREATE_VIDEO, CREATED_VERSUS, NOTICE, CREATED_TEAM_UP }
}