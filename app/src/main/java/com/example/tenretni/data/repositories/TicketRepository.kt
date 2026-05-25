package com.example.tenretni.data.repositories


import com.example.tenretni.data.datasources.TicketDataSource
import com.example.tenretni.models.Ticket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TicketRepository(
    private val ticketDataSource: TicketDataSource = TicketDataSource()
) {

    fun retrieveAll(): Flow<List<Ticket>> {
        return flow {
            emit(ticketDataSource.retrieveAll())
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }

    fun updateStatus(ticketId: String, status: String): Flow<Ticket> {
        return flow {
            emit(ticketDataSource.updateStatus(ticketId, status))
        }.catch { ex ->
            throw ex
        }.flowOn(Dispatchers.IO)
    }
}