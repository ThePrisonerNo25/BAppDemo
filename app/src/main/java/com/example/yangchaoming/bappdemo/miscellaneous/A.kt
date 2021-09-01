package com.example.yangchaoming.bappdemo.miscellaneous

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

class A {
    private fun a() {
        val strings = arrayOf(Manifest.permission.READ_CONTACTS)

    }

//    private fun getContacts(data: Intent?) {
//        if (data == null) {
//            return
//        }
//
//        val contactData = data.data ?: return
//        var name = ""
//        var phoneNumber = ""
//
//        val contactUri = data.data
//        val cursor = getContentResolver().query(contactUri, null, null, null, null)
//        if (cursor.moveToFirst()) {
//            name = cursor
//                    .getString(cursor
//                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//            var hasPhone = cursor
//                    .getString(cursor
//                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
//            val id = cursor.getString(cursor
//                    .getColumnIndex(ContactsContract.Contacts._ID))
//            if (hasPhone.equals("1", ignoreCase = true)) {
//                hasPhone = "true"
//            } else {
//                hasPhone = "false"
//            }
//            if (java.lang.Boolean.parseBoolean(hasPhone)) {
//                val phones = getContentResolver()
//                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                        + " = " + id, null, null)
//                while (phones.moveToNext()) {
//                    phoneNumber = phones
//                            .getString(phones
//                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                }
//                phones.close()
//            }
//            cursor.close()
//
//            //            et_username.setText(name);
//            //            et_phone.setText(phoneNumber);
//        }
//    }
}
