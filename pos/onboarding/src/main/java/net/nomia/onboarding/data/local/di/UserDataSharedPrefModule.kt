package net.nomia.onboarding.data.local.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UserDataSharedPrefModule {

    companion object {
        @Qualifier
        @Retention(AnnotationRetention.BINARY)
        annotation class SharedPreferenceUserData

        @Provides
        @Singleton
        @SharedPreferenceUserData
        fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("SharedPreferenceUserData", Context.MODE_PRIVATE)
    }
}

