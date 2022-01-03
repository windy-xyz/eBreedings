package com.wcs.ebreedings.ui.sync

import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.entity.VegMeasEntity

data class SyncModel(
    val ffb: List<FfbYieldEntity>,
    val veg: List<VegMeasEntity>
)
