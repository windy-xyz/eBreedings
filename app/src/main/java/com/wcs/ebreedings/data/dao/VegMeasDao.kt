package com.wcs.ebreedings.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wcs.ebreedings.data.entity.VegMeasEntity

@Dao
interface VegMeasDao {
    @Query("select * from veg_measurement")
    fun getAllMeasurements(): List<VegMeasEntity>

    @Query("select * from veg_measurement where measurement_id = :id")
    fun getMeasurementById(id: Int): VegMeasEntity

    @Insert
    fun insertNewMeasurement(vegMeasEntity: VegMeasEntity)

    @Query("delete from veg_measurement")
    fun deleteAll()

    @Query("update veg_measurement set " +
            "leaf_sam_no = :leaf_sam_no, " +
            "palm_height = :palm_height, " +
            "petiole_width = :petiole_width, " +
            "petiole_thickness = :petiole_thickness, " +
            "pet_cross_sec = :pet_cross_sec, " +
            "leaflets_sam1_width = :leaflets_sam1_width, " +
            "leaflets_sam1_length = :leaflets_sam1_length, " +
            "leaflets_sam2_width = :leaflets_sam2_width, " +
            "leaflets_sam2_length = :leaflets_sam2_length, " +
            "leaflets_sam3_width = :leaflets_sam3_width, " +
            "leaflets_sam3_length = :leaflets_sam3_length, " +
            "leaflets_sam4_width = :leaflets_sam4_width, " +
            "leaflets_sam4_length = :leaflets_sam4_length, " +
            "leaflets_sam5_width = :leaflets_sam5_width, " +
            "leaflets_sam5_length = :leaflets_sam5_length, " +
            "leaflets_sam6_width = :leaflets_sam6_width, " +
            "leaflets_sam6_length = :leaflets_sam6_length, " +
            "num_of_leaflets = :num_of_leaflets, " +
            "palms_frond_length = :palms_frond_length, " +
            "palms_frond_prod = :palms_frond_prod, " +
            "leaf_area = :leaf_area, " +
            "palm_trunk_circum = :palm_trunk_circum, " +
            "supervision = :supervision, " +
            "record_officer = :record_officer " +
            "where measurement_id = :id")
    fun updateMeasurement(
        id: Int,
        leaf_sam_no: Int,
        palm_height: Double,
        petiole_width: Double,
        petiole_thickness: Double,
        pet_cross_sec: Double,
        leaflets_sam1_width: Double,
        leaflets_sam1_length: Double,
        leaflets_sam2_width: Double,
        leaflets_sam2_length: Double,
        leaflets_sam3_width: Double,
        leaflets_sam3_length: Double,
        leaflets_sam4_width: Double,
        leaflets_sam4_length: Double,
        leaflets_sam5_width: Double,
        leaflets_sam5_length: Double,
        leaflets_sam6_width: Double,
        leaflets_sam6_length: Double,
        num_of_leaflets: Double,
        palms_frond_length: Double,
        palms_frond_prod: Double,
        leaf_area: Double,
        palm_trunk_circum: Double,
        supervision: String,
        record_officer: String,
    )
}
