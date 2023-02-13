package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.dao.ItemsDao
import com.varietapp.NotaLs.data.Items
import kotlinx.coroutines.flow.Flow

class ItemsRepoImplement(private val itemsDao:ItemsDao):ItemsRepo {
    override suspend fun insertItem(item: Items) {
        itemsDao.insertItem(item)
    }

    override suspend fun deleteItem(item: Items) {
        itemsDao.deleteItem(item)
    }

    override suspend fun getItemsById(id: Int): Items? {
        return itemsDao.getItemsById(id)
    }

    override fun getAllItems(): Flow<List<Items>> {
        return itemsDao.getAllItems()
    }

    override fun getItemsByShopId(shopId: Int): Flow<List<Items>> {
       return itemsDao.getItemsByShopId(shopId)
    }

}