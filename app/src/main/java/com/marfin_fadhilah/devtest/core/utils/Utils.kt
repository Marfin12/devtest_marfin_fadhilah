package com.marfin_fadhilah.devtest.core.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.io.File

object Utils {
    fun EditText.validateInput(
        error: String,
        validate: ((String) -> Boolean)
    ) {
        val curEdt = this

        curEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                curEdt.error = if (validate.invoke(s.toString())) null
                else error
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun isContainFile(vararg filenames: String): Boolean = filenames.any { filename ->
        return try {
            val file = File(filename)
            file.exists()
        } catch (e: Exception) {
            false
        }
    }
}