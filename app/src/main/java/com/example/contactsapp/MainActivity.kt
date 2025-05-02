package com.example.contactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.contactsapp.ui.screens.ContactsApp
import com.example.contactsapp.ui.theme.ContactsAppTheme
import com.example.presentation.viewmodel.ContactsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContactsAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ContactsApp(viewModel = viewModel)
                }
            }
        }
    }
}
