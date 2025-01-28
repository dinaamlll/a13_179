package com.example.a13_179.repository

import com.example.a13_179.model.Event
import com.example.a13_179.service.EventService
import java.io.IOException

interface EventRepository {
    suspend fun getAllEvent(): List<Event>
    suspend fun insertEvent(event: Event)
    suspend fun updateEvent(id_event: Int, event: Event)
    suspend fun deleteEvent(id_event: Int)
    suspend fun getEventById(id_event: Int): Event
}

class NetworkEventRepository(
    private val eventApiService: EventService
) : EventRepository {
    override suspend fun getAllEvent(): List<Event> =
        eventApiService.getAllEvent().data

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(id_event: Int, event: Event) {
        eventApiService.updateEvent(id_event, event)
    }

    override suspend fun deleteEvent(id_event: Int) {
        try {
            val response = eventApiService.deleteEvent(id_event)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete event. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEventById(id_event: Int): Event {
        return eventApiService.getEventById(id_event).data
    }
}
