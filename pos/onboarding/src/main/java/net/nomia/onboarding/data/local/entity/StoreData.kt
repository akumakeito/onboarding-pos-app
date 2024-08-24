package net.nomia.onboarding.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "stores")
data class StoreData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val uuid: UUID?,
    val name: String,
    @Embedded
    val organization: OrganizationData?,
    @Embedded
    val areaMetrics : AreaMetricsData?,
    val catalogId : UUID?,
    val actualAddress: String,
    val storeTypes: List<StoreTypeData>?,
    val serviceTypes: List<ServiceTypeData>?,
    val previousErp : String?
)