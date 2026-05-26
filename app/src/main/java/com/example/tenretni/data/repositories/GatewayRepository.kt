package com.example.tenretni.data.repositories

import com.example.tenretni.data.datasources.GatewayDataSource
import com.example.tenretni.models.Gateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import kotlinx.coroutines.flow.map

class GatewayRepository(
    private val gatewayDataSource: GatewayDataSource = GatewayDataSource()
) {

    fun retrieveAll(): Flow<List<Gateway>> {
        return flow {
            emit(gatewayDataSource.retrieveAll())
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }

    fun retrieveOne(serialNumber: String): Flow<Gateway?> {
        return retrieveAll().map { gateways ->
            gateways.find { it.serialNumber == serialNumber }
        }
    }

    fun retrieveCustomerGateways(customerHref: String): Flow<List<Gateway>> {
        return flow {
            emit(gatewayDataSource.retrieveCustomerGateways(customerHref))
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }

    fun installCustomerGateway(customerHref: String?, gatewayInfo: String): Flow<String> {
        return flow {
            emit(gatewayDataSource.installCustomerGateway(customerHref, gatewayInfo))
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }

    fun updateGateway(gatewayHref: String): Flow<Gateway> {
        return flow {
            emit(gatewayDataSource.updateGateway(gatewayHref))
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }
}