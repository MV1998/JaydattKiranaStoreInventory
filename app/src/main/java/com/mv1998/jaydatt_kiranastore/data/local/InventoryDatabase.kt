package com.mv1998.jaydatt_kiranastore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mv1998.jaydatt_kiranastore.data.local.convertors.DateConvertors
import com.mv1998.jaydatt_kiranastore.data.local.dao.ProductDAO
import com.mv1998.jaydatt_kiranastore.data.local.dao.ReportDAO
import com.mv1998.jaydatt_kiranastore.data.local.entities.ProductEntity
import com.mv1998.jaydatt_kiranastore.data.local.entities.ReportEntity

@Database(entities = [ProductEntity::class, ReportEntity::class], version = 3)
@TypeConverters(value = [DateConvertors::class])
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDAO

    abstract fun getReportDao(): ReportDAO

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE 'reports' ADD COLUMN 'month' INTEGER NOT NULL DEFAULT 0;")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE 'reports' ADD COLUMN 'purchase' INTEGER NOT NULL DEFAULT 0;")
            }
        }

        fun getDatabase(context: Context): InventoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryDatabase::class.java,
                    "jay_inventory_system_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                   // .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}