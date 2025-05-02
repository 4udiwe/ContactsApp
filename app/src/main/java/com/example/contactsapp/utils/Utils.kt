package com.example.contactsapp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

/**
 * Проверяет наличие разрешений на доступ к контактам устройства.
 *
 * @param context
 * @param onPermissionGranted вызывается при предоставлении разрешения
 * @param onPermissionDenied вызывается при отклонении разрешения
 * @param requestPermission вызывается при запросе разрешения
 */
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


/**
 * Проверяет наличие разрешений на осуществление звонков с устройства.
 *
 * @param context
 * @param onPermissionGranted вызывается при предоставлении разрешения
 * @param onPermissionDenied вызывается при отклонении разрешения
 * @param requestPermission вызывается при запросе разрешения
 */
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


/**
 * Совершает звонок по телефонному номеру.
 *
 * @param context
 * @param phoneNumber номер для звонка
 */
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