package com.example.presentation.viewmodel

import com.example.domain.model.ContactModel

sealed interface ContactsState {
    data object Idle : ContactsState
    data object Loading : ContactsState
    data class Success(val contacts: List<ContactModel>) : ContactsState
    data object Error : ContactsState
}