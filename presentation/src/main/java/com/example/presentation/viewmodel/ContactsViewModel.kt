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

    private val _contactListStateFlow = MutableStateFlow<List<ContactModel>>(emptyList())
    val contactList = _contactListStateFlow.asStateFlow()

    private fun loadContacts() = viewModelScope.launch {
        _contactListStateFlow.value = useCase()
    }

    init {
        loadContacts()
    }
}