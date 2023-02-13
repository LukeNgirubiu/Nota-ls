package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.dao.ShoppingDao
import com.varietapp.NotaLs.data.Shopping
import kotlinx.coroutines.flow.Flow
class ShoppingRepoImplement(private val shoppingDao:ShoppingDao):ShoppingRepository {
    override suspend fun insertShopping(shopping: Shopping) {
       shoppingDao.insertShopping(shopping)
    }
    override suspend fun deleteShopping(shopping: Shopping) {
        shoppingDao.deleteShopping(shopping)
    }
    override suspend fun getShoppingById(id: Int): Shopping? {
        return shoppingDao.getShoppingById(id)
    }
    override fun getAllShopping(): Flow<List<Shopping>> {
       return shoppingDao.getAllShopping()
    }

}