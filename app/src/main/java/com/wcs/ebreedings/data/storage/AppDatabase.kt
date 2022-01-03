package com.wcs.ebreedings.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wcs.ebreedings.data.dao.FfbYieldDao
import com.wcs.ebreedings.data.dao.PalmDao
import com.wcs.ebreedings.data.dao.VegMeasDao
import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.entity.PalmEntity
import com.wcs.ebreedings.data.entity.VegMeasEntity

@Database(
    entities = [PalmEntity::class, FfbYieldEntity::class, VegMeasEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun PalmDao(): PalmDao
    abstract fun FfbYieldDao() : FfbYieldDao
    abstract fun VegMeasDao(): VegMeasDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "eBreedingsDatabase").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
