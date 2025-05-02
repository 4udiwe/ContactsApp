package com.example.contactsapp.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.contactsapp.utils.checkCallPermission
import com.example.contactsapp.utils.checkContactsPermission
import com.example.contactsapp.utils.makeCall
import com.example.presentation.screens.ContactsScreen
import com.example.presentation.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

@Composable
fun ContactsApp(viewModel: ContactsViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showCallDialog by remember { mutableStateOf(false) }
    var showPermissionRationale by remember { mutableStateOf(false) }
    var currentPhoneNumber by remember { mutableStateOf("") }
    var permissionType by remember { mutableStateOf("") }

    val contactsPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.loadContacts()
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Contacts permission denied",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    val callPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            makeCall(context, currentPhoneNumber)
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Call permission denied",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        checkContactsPermission(
            context = context,
            onPermissionGranted = { viewModel.loadContacts() },
            onPermissionDenied = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Contacts permission required",
                        duration = SnackbarDuration.Long
                    )
                }
            },
            requestPermission = { contactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ContactsScreen(
            paddingValues = innerPadding,
            viewModel = viewModel,
            onContactClicked = { contact ->
                currentPhoneNumber = contact.phone
                checkCallPermission(
                    context = context,
                    onPermissionGranted = { makeCall(context, contact.phone) },
                    onPermissionDenied = { showCallDialog = true },
                    requestPermission = {
                        permissionType = "call"
                        showPermissionRationale = true
                    }
                )
            }
        )
    }

    if (showCallDialog) {
        AlertDialog(
            onDismissRequest = { showCallDialog = false },
            title = { Text("Make a call") },
            text = { Text("Call $currentPhoneNumber?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCallDialog = false
                        checkCallPermission(
                            context = context,
                            onPermissionGranted = { makeCall(context, currentPhoneNumber) },
                            onPermissionDenied = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Call permission required",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            requestPermission = {
                                permissionType = "call"
                                showPermissionRationale = true
                            }
                        )
                    }
                ) {
                    Text("Call")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCallDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showPermissionRationale) {
        AlertDialog(
            onDismissRequest = { showPermissionRationale = false },
            title = { Text("Permission needed") },
            text = {
                Text(
                    if (permissionType == "call") {
                        "This app needs call permission to make calls directly"
                    } else {
                        "This app needs contacts permission to display your contacts"
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionRationale = false
                        if (permissionType == "call") {
                            callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                        } else {
                            contactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPermissionRationale = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}


