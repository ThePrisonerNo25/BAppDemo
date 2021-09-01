package com.example.yangchaoming.bappdemo.miscellaneous

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.acitity_test6.*

class TestActivity6 : AppCompatActivity(){

    companion object{
        const val REQUSET_CODE_CONTACTS = 102
        const val PERMISSION_CODE_CONTACTS = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_test6)

        btn_search_contract.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(arrayOf<String>(Manifest.permission.READ_CONTACTS), PERMISSION_CODE_CONTACTS);
            }else{
                val intent =  Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUSET_CODE_CONTACTS);
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUSET_CODE_CONTACTS){
                getContacts(data);
            }
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =  Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUSET_CODE_CONTACTS);
            } else {
                Toast.makeText(this, "你拒绝了此应用对读取联系人权限的申请！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun getContacts(data: Intent?) {
        if (data == null) {
            return
        }

        val contactData = data.data ?: return
        var name = ""
        var phoneNumber = ""

        val contactUri = data.data ?: return
        val cursor = contentResolver.query(contactUri, null, null, null, null) ?: return
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            var hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            val id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID))
            if (hasPhone.equals("1", ignoreCase = true)) {
                hasPhone = "true"
            } else {
                hasPhone = "false"
            }
            if (java.lang.Boolean.parseBoolean(hasPhone)) {
                val phones = contentResolver
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null) ?: return
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                }
                phones.close()
            }
            cursor.close()

            tv_name.text = name
            tv_phone.text = phoneNumber;
        }
    }
}