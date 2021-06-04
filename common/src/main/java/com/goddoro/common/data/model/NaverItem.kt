package com.goddoro.common.data.model

import com.goddoro.common.R
import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng


/**
 * created By DORO 2020/09/12
 */

data class NaverItem(var position: LatLng) : TedClusterItem {

    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(position.latitude, position.longitude)
    }

    /**
     * Set the title of the marker
     * @param title string to be set as title
     */
    var title: String? = null
    /**
     * Set the description of the marker
     * @param snippet string to be set as snippet
     */
    var snippet: String? = null

    var profileSource : Int? = R.drawable.sample_image_01

    constructor(lat: Double, lng: Double) : this(LatLng(lat, lng)) {
        title = null
        snippet = null
    }

    constructor(lat: Double, lng: Double, title: String?, snippet: String?) : this(
        LatLng(lat, lng)
    ) {
        this.title = title
        this.snippet = snippet
    }
}