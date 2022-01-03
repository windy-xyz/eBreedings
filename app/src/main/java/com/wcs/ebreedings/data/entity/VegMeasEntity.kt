package com.wcs.ebreedings.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="veg_measurement")
data class VegMeasEntity(
    @PrimaryKey(autoGenerate = true)
    val measurement_id: Int = 0,
    var palm_code: String,
    var short_palm_code: String,
    var row_number: String,
    var date_veg_meas: String,
    var block_code: String,
    var leaf_sam_no: Int,
    var palm_height: Double,
    var petiole_width: Double,
    var petiole_thickness: Double,
    var pet_cross_sec: Double,
    var leaflets_sam1_width: Double,
    var leaflets_sam1_length: Double,
    var leaflets_sam2_width: Double,
    var leaflets_sam2_length: Double,
    var leaflets_sam3_width: Double,
    var leaflets_sam3_length: Double,
    var leaflets_sam4_width: Double,
    var leaflets_sam4_length: Double,
    var leaflets_sam5_width: Double,
    var leaflets_sam5_length: Double,
    var leaflets_sam6_width: Double,
    var leaflets_sam6_length: Double,
    var num_of_leaflets: Double,
    var palms_frond_length: Double,
    var palms_frond_prod: Double,
    var leaf_area: Double,
    var palm_trunk_circum: Double,
    var supervision: String,
    var record_officer: String,
    var create_date: String,
    var create_by: String
)
