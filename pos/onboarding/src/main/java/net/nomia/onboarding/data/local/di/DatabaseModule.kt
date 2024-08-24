package net.nomia.onboarding.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.nomia.onboarding.data.local.StoresDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StoresDatabase =
        Room.databaseBuilder(
            context,
            StoresDatabase::class.java,
            "stores"
        ).build()

    @Singleton
    @Provides
    fun provideStoreDao(database: StoresDatabase) = database.storeDao()
}
