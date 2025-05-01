package com.example.data.repository

import com.example.domain.datasource.ContactDataSource
import com.example.domain.model.ContactModel
import com.example.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val dataSource: ContactDataSource
) : ContactRepository {
    override suspend fun getContacts(): List<ContactModel> = dataSource.getContacts()
}