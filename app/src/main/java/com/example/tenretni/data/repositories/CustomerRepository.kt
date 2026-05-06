package com.example.tenretni.data.repositories

import com.example.tenretni.data.datasources.CustomerDataSource
import com.example.tenretni.models.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CustomerRepository (
    private val customerDataSource: CustomerDataSource = CustomerDataSource()
) {

    fun retrieveAll(): Flow<List<Customer>> {
        return flow {
            emit(customerDataSource.retrieveAll())
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }
}