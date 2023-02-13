package com.varietapp.NotaLs

import android.app.Application
import androidx.room.Room
import com.varietapp.NotaLs.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
     @Provides
     @Singleton
    fun provideNotalsDatabase(app:Application):TableInstance{
     return Room.databaseBuilder(app,
         TableInstance::class.java,
         "nota_ls"
     ).build()
    }
    @Provides
    @Singleton
    fun provideEventRepository(db:TableInstance):EventRepository{
       return EventRepositoryImplement(db.eventDao)
    }
    @Provides
    @Singleton
    fun provideEventActivityRepository(db:TableInstance):EventActRepository{
        return EventActRepositoryImplement(db.eventActivityDao)
    }
    @Provides
    @Singleton
    fun provideShoppingRepository(db:TableInstance):ShoppingRepository{
        return ShoppingRepoImplement(db.shoppingDao)
    }
    @Provides
    @Singleton
    fun provideItemsRepository(db:TableInstance):ItemsRepo{
        return ItemsRepoImplement(db.itemsDao)
    }
    @Provides
    @Singleton
    fun provideServiceRepository(db:TableInstance):ServiceRepository{
        return ServiceRepositoryImplementation(db.serviceDao)
    }
}