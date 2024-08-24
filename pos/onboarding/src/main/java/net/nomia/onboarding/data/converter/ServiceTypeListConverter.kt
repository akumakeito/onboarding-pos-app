package net.nomia.onboarding.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.nomia.onboarding.data.local.entity.ServiceTypeData

class ServiceTypeListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromServiceTypeDataList(value: List<ServiceTypeData>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toServiceTypeDataList(value: String?): List<ServiceTypeData>? {
        val listType = object : TypeToken<List<ServiceTypeData>>() {}.type
        return gson.fromJson(value, listType)
    }
}