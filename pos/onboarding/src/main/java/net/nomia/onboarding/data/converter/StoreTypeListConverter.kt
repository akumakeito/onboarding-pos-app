package net.nomia.onboarding.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.nomia.onboarding.data.local.entity.StoreTypeData

class StoreTypeListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStoreTypeDataList(value: List<StoreTypeData>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStoreTypeDataList(value: String?): List<StoreTypeData>? {
        val listType = object : TypeToken<List<StoreTypeData>>() {}.type
        return gson.fromJson(value, listType)
    }
}