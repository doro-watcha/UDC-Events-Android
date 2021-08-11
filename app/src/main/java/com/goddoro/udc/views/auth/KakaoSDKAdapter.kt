package com.goddoro.udc.views.auth

import android.app.Application
import com.kakao.auth.*


class KakaoSDKAdapter(application: Application) : KakaoAdapter() {
    private val mApplication: Application
    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_TALK)
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

            override fun isSecureMode(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
            }
        }
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig { mApplication }
    }

    init {
        mApplication = application
    }
}
