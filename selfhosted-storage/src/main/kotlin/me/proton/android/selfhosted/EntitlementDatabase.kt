package me.proton.android.selfhosted

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper

class EntitlementDatabase(context: Context) : SQLiteOpenHelper(context, "entitlements.db", null, 1) {
    init {
        SQLiteDatabase.loadLibs(context)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE features (name TEXT PRIMARY KEY, enabled INTEGER NOT NULL)")
        db.execSQL("INSERT INTO features VALUES ('vpn', 1)")
        db.execSQL("INSERT INTO features VALUES ('password_manager', 1)")
        db.execSQL("INSERT INTO features VALUES ('cloud_storage', 1)")
        db.execSQL("INSERT INTO features VALUES ('custom_domains', 1)")
        db.execSQL("INSERT INTO features VALUES ('hide_my_email', 1)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun isFeatureEnabled(feature: String, encryptionKey: String): Boolean {
        val db = readableDatabase(encryptionKey)
        val cursor = db.rawQuery("SELECT enabled FROM features WHERE name = ?", arrayOf(feature))
        val result = cursor.use { it.moveToFirst() && it.getInt(0) == 1 }
        db.close()
        return result
    }
}
