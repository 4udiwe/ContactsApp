package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ContactModel
import com.example.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val useCase: GetContactsUseCase
) : ViewModel(){

    private val _contactsState = MutableStateFlow<ContactsState>(ContactsState.Idle)
    val contactsState = _contactsState.asStateFlow()

    private var allContacts: List<ContactModel> = emptyList()

    fun loadContacts() = viewModelScope.launch {
        _contactsState.value = ContactsState.Loading
        try {
            allContacts = useCase()
            if (allContacts.isEmpty()) {
                _contactsState.value = ContactsState.Error
            } else {
                _contactsState.value = ContactsState.Success(contacts = allContacts)
            }
        } catch (e: Exception) {
            _contactsState.value = ContactsState.Error
        }
    }

    fun filterContactsByName(query: String) {
        _contactsState.value = ContactsState.Loading

        val filtered = if (query.isBlank()) {
            allContacts
        } else {
            val regex = Regex(".*${Regex.escape(query)}.*", RegexOption.IGNORE_CASE)
            allContacts.filter { regex.containsMatchIn(it.name) }
        }

        _contactsState.value = if (filtered.isEmpty()) {
            ContactsState.Error
        } else {
            ContactsState.Success(contacts = filtered)
        }
    }
}