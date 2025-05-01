package com.example.data.datasource

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.domain.datasource.ContactDataSource
import com.example.domain.model.ContactModel
import java.util.UUID

class ContactDataSourceImlp(
    private val contentResolver: ContentResolver
): ContactDataSource {
    @SuppressLint("Range")
    override suspend fun getContacts(): List<ContactModel> {
        val contacts = mutableListOf<ContactModel>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )
                val phone = it.getString(
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
                contacts.add(ContactModel(id = UUID.randomUUID().toString(), name = name, phone = phone))
            }
        }
        return contacts
    }
}