package com.example.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ContactModel
import com.example.presentation.ui.ContactItem
import com.example.presentation.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(
    paddingValues: PaddingValues,
    viewModel: ContactsViewModel,
    onContactClicked: (ContactModel) -> Unit
) {
    val contacts = viewModel.contactList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ){
        Text(text = "Контакты", fontSize = 26.sp, fontWeight = FontWeight.SemiBold)

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(contacts.value){ contact ->
                ContactItem(
                    contact = contact,
                    onClick = {
                        onContactClicked(contact)
                    }
                )
            }
        }
    }

}