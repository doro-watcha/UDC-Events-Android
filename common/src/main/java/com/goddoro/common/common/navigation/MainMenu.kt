package com.goddoro.common.common.navigation

import androidx.annotation.IdRes
import com.goddoro.common.R


/**
 * created By DORO 2020/08/16
 */

enum class MainMenu(@IdRes override val menuId: Int, override val idx: Int) : IMainMenu {

    HOME(R.id.nav_item_home, 0),
    EVENT(R.id.nav_item_event, 1),
    CLASS(R.id.nav_item_class, 2),
    PROFILE(R.id.nav_item_video, 3)

    ;

    companion object {
        fun parseIdToIdx(@IdRes id: Int) = values().indexOfFirst { it.menuId == id }
        fun parseIdToMainMenu(@IdRes id: Int) = values().first { it.menuId == id }
        fun parseIndexToMainMenu(idx: Int) = values().first { it.idx == idx }
    }
}