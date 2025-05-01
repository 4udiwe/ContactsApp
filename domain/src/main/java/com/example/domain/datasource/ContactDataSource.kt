package com.example.domain.datasource

import com.example.domain.model.ContactModel

interface ContactDataSource {
    suspend fun getContacts() : List<ContactModel>
}