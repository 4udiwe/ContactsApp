package com.example.domain.usecase

import com.example.domain.repository.ContactRepository

class GetContactsUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke() = repository.getContacts()
}