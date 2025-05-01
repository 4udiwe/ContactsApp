package com.example.domain.repository

import com.example.domain.model.ContactModel

interface ContactRepository {
    suspend fun getContacts() : List<ContactModel>
}