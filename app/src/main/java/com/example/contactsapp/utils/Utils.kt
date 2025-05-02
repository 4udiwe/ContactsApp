package com.example.contactsapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

internal fun checkContactsPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    requestPermission: () -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGranted()
        }
        (context as ComponentActivity).shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
            onPermissionDenied()
            requestPermission()
        }
        else -> {
            requestPermission()
        }
    }
}


internal fun checkCallPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    requestPermission: () -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGranted()
        }
        (context as ComponentActivity).shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
            onPermissionDenied()
        }
        else -> {
            requestPermission()
        }
    }
}

internal fun makeCall(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.cause} - ${e.message}", Toast.LENGTH_SHORT).show()
    }
}