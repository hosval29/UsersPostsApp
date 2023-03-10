package com.example.userpostapp.di

import android.app.Application
import androidx.room.Room
import com.example.userpostapp.BuildConfig
import com.example.userpostapp.data.local.database.UserPostDatabase
import com.example.userpostapp.data.remote.api.UsersPostsApi
import com.example.userpostapp.data.repository.UsersPostsRepositoryImpl
import com.example.userpostapp.domain.repository.UsersPostsRepository
import com.example.userpostapp.domain.use_case.GetAllUser
import com.example.userpostapp.domain.use_case.GetUserPostById
import com.example.userpostapp.domain.use_case.SearchUser
import com.example.userpostapp.domain.use_case.UsersPostsCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideSeriesApi(retrofit: Retrofit): UsersPostsApi {
        return retrofit.create(UsersPostsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPostDatabase(
        app: Application
    ): UserPostDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            UserPostDatabase::class.java,
            "user_post_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        database: UserPostDatabase,
        usersPostsApi: UsersPostsApi
    ) : UsersPostsRepository {
        return UsersPostsRepositoryImpl(
            userDao = database.userDao,
            postDao = database.postDao,
            usersPostsApi = usersPostsApi
        )
    }

    @Provides
    @Singleton
    fun provideUserPostCase(
        repository: UsersPostsRepository
    ) : UsersPostsCase {
        return UsersPostsCase(
            getAllUser = GetAllUser(repository = repository),
            getUserPostById = GetUserPostById(repository = repository),
            searchUser = SearchUser(repository)
        )
    }

}