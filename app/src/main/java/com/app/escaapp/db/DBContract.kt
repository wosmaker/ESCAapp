package com.example.management

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_ID = "id"
            val COLUMN_RELATE_NAME = "relate_name"
            val COLUMN_PHONE_NO = "phone_no"
            val COLUMN_RELATION = "relation"
        }
    }
}