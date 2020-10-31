package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.DanceClass


/**
 * created By DORO 2020/10/24
 */

class ClassShopViewModel() : ViewModel() {


    val mainClasses: MutableLiveData<List<DanceClass>> = MutableLiveData()

    val normalClasses : MutableLiveData<List<DanceClass>> = MutableLiveData()

    val workshopClasses : MutableLiveData<List<DanceClass>> = MutableLiveData()

    init {

        mainClasses.value = listOf(
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            )
        )

        normalClasses.value = listOf(
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            )
        )

        workshopClasses.value = listOf(
            DanceClass(
                0,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                1,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            ),
            DanceClass(
                2,
                "https://s3.ap-northeast-2.amazonaws.com/cdn.onesongtwoshows.com/video/6ukirdylqb4_1602523168390.jpg"
            )
        )

    }


}