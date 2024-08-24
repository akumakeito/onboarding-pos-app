package net.nomia.onboarding.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.nomia.onboarding.data.converter.ServiceTypeListConverter
import net.nomia.onboarding.data.converter.StoreTypeListConverter
import net.nomia.onboarding.data.local.entity.StoreData
import net.nomia.pos.core.converter.CurrencyConverter
import net.nomia.pos.core.converter.UUIDConverter

@Database([StoreData::class], version = 1)
@TypeConverters(
    value = [
        CurrencyConverter::class,
        UUIDConverter::class,
        StoreTypeListConverter::class,
        ServiceTypeListConverter::class

    ]
)
abstract class StoresDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
}