package com.example.data.datasource

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.example.domain.model.ContactModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContactDataSourceImplTest {

    private lateinit var contentResolver: ContentResolver
    private lateinit var cursor: Cursor
    private lateinit var dataSource: ContactDataSourceImpl

    @Before
    fun setUp() {
        contentResolver = mockk(relaxed = true)
        cursor = mockk(relaxed = true)
        dataSource = ContactDataSourceImpl(contentResolver)
    }

    @Test
    fun `getContacts should return empty list when no contacts available`() = runTest {
        // Arrange
        every { contentResolver.query(any(), any(), any(), any(), any()) } returns null

        // Act
        val result = dataSource.getContacts()

        // Assert
        assertEquals(0, result.size)
    }

    @Test
    fun `getContacts should return correct contacts list`() = runTest {
        // Arrange
        val expectedContacts = listOf(
            ContactModel(id = "1", name = "John Doe", phone = "+1234567890"),
            ContactModel(id = "2", name = "Jane Smith", phone = "+0987654321")
        )

        mockCursorWithContacts(expectedContacts)

        // Act
        val result = dataSource.getContacts()

        // Assert
        assertEquals(expectedContacts.size, result.size)
        assertEquals(expectedContacts[0].name, result[0].name)
        assertEquals(expectedContacts[1].phone, result[1].phone)
        verify { cursor.close() }
    }

    @Test
    fun `getContacts should handle empty name or phone fields`() = runTest {
        // Arrange
        val expectedContacts = listOf(
            ContactModel(id = "1", name = "", phone = "+1234567890"),
            ContactModel(id = "2", name = "No Phone", phone = "")
        )

        mockCursorWithContacts(expectedContacts)

        // Act
        val result = dataSource.getContacts()

        // Assert
        assertEquals("", result[0].name)
        assertEquals("", result[1].phone)
    }

    @Test
    fun `getContacts should sort contacts by name ascending`() = runTest {
        // Arrange
        val sortedContacts = listOf(
            ContactModel(id = "2", name = "Alpha", phone = "222"),
            ContactModel(id = "1", name = "Zeta", phone = "111")
        )

        mockCursorWithContacts(sortedContacts)

        // Act
        val result = dataSource.getContacts()

        // Assert
        assertEquals("Alpha", result[0].name)
        assertEquals("Zeta", result[1].name)
    }

    private fun mockCursorWithContacts(contacts: List<ContactModel>) {
        // Мок запроса
        every {
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
        } returns cursor

        // Мок поведения курсора
        var currentPosition = -1
        every { cursor.moveToNext() } answers {
            currentPosition++
            currentPosition < contacts.size
        }

        val displayNameIndex = 0
        val phoneNumberIndex = 1

        every { cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME) } returns displayNameIndex
        every { cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) } returns phoneNumberIndex

        // Мок данных курсора
        every { cursor.getString(displayNameIndex) } answers {
            contacts.getOrNull(currentPosition)?.name ?: ""
        }
        every { cursor.getString(phoneNumberIndex) } answers {
            contacts.getOrNull(currentPosition)?.phone ?: ""
        }
    }
}