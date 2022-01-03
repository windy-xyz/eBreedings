package com.wcs.ebreedings.data.model

import com.google.gson.annotations.SerializedName

data class Palm(
    @SerializedName("palm_code") val palm_code: String,
    @SerializedName("palm_number") val palm_number: String,
    @SerializedName("palm_row_number") val palm_row_number: String,
    @SerializedName("progeny_id") val progeny_id: String,
    @SerializedName("progeny_desc") val progeny_desc: String,
    @SerializedName("parental_male") val parental_male: String,
    @SerializedName("parental_male_desc") val parental_male_desc: String,
    @SerializedName("parental_female") val parental_female: String,
    @SerializedName("parental_female_desc") val parental_female_desc: String,
    @SerializedName("fruit_type") val fruit_type: String,
    @SerializedName("estate_code") val estate_code: String,
    @SerializedName("estate_name") val estate_name: String,
    @SerializedName("division_code") val division_code: String,
    @SerializedName("division_name") val division_name: String,
    @SerializedName("block_no") val block_no: String,
    @SerializedName("trial_area") val trial_area: String,
    @SerializedName("year_of_planting") val year_of_planting: String,
    @SerializedName("mature_stage") val mature_stage: String,
    @SerializedName("palm_status") val palm_status: String,
    @SerializedName("palm_status2") val palm_status2: String,
    @SerializedName("qr_code_no") val qr_code_no: String,
    @SerializedName("max_bunches") val max_bunches: String
)
