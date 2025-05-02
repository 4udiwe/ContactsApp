package com.example.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ContactModel
import com.example.presentation.ui.ContactItem
import com.example.presentation.viewmodel.ContactsState
import com.example.presentation.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    paddingValues: PaddingValues,
    viewModel: ContactsViewModel,
    onContactClicked: (ContactModel) -> Unit,
) {
    val contactsState by viewModel.contactsState.collectAsState()
    val query = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Контакты",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DockedSearchBar(
            query = query.value,
            onQueryChange = { newQuery ->
                query.value = newQuery
                viewModel.filterContactsByName(newQuery)
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = {
                Text("Поиск контактов по имени")
            }
        ) {}

        when (contactsState) {
            ContactsState.Error -> ErrorState()
            ContactsState.Idle -> IdleState()
            ContactsState.Loading -> LoadingState()
            is ContactsState.Success -> {
                ContactList(
                    contacts = (contactsState as ContactsState.Success).contacts,
                    onContactClicked = onContactClicked
                )
            }
        }
    }
}

@Composable
private fun ContactList(
    contacts: List<ContactModel>,
    onContactClicked: (ContactModel) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        items(contacts) { contact ->
            ContactItem(
                contact = contact,
                onClick = { onContactClicked(contact) }
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun IdleState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Загрузите контакты вашего устройства")
    }
}

@Composable
private fun ErrorState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ошибка при загрузке контактов")
    }
}