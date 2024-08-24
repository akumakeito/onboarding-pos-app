package net.nomia.onboarding.data.local.entity

import androidx.room.ColumnInfo
import java.util.Currency
import java.util.UUID

data class OrganizationData(
    @ColumnInfo(name = "organization_id")
    val id: UUID,
    @ColumnInfo(name = "organization_name")
    val name: String,
    val code: String,
    val currency: Currency

)