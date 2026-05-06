package com.example.tenretni.data.repositories

import com.example.tenretni.data.datasources.NetworkDataSource

import com.example.tenretni.models.Network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkRepository(
    private val networkDataSource: NetworkDataSource = NetworkDataSource()
) {

    fun retrieveAll(): Flow<List<Network>> {
        return flow {
            emit(networkDataSource.retrieveAll())
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }
}