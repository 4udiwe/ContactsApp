package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val useCase: GetContactsUseCase
) : ViewModel(){

    private val _contactsState = MutableStateFlow<ContactsState>(ContactsState.Idle)
    val contactsState = _contactsState.asStateFlow()

    fun loadContacts() = viewModelScope.launch {
        _contactsState.value = ContactsState.Loading

        val result = useCase()
        if (result.isEmpty())
            _contactsState.value = ContactsState.Error

        _contactsState.value = ContactsState.Success(contacts = result)
    }
}