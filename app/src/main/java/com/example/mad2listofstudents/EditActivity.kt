package com.example.mad2listofstudents

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity()
{
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextSecondName: EditText
    private lateinit var editTextMiddleName: EditText
    private lateinit var editTextBirthDay: EditText
    private lateinit var editTextFaculty: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val action = intent.getSerializableExtra("ACTION") as Int
        val index = intent.getSerializableExtra("INDEX") as Int

        editTextFirstName = findViewById(R.id.editText1)
        editTextSecondName = findViewById(R.id.editText2)
        editTextMiddleName = findViewById(R.id.editText3)
        editTextBirthDay = findViewById(R.id.editText4)
        editTextFaculty = findViewById(R.id.editText5)

        findViewById<Button>(R.id.buttonConfirmChanges).setOnClickListener { confirmChanges(action, index) }
    }

    private fun confirmChanges(action: Int, index: Int)
    {
        if (editTextFirstName.text.toString() != "" && editTextSecondName.text.toString() != ""
            && editTextMiddleName.text.toString() != "" && editTextBirthDay.text.toString() != ""
            && editTextFaculty.text.toString() != "")
        {
            val intent = Intent(this@EditActivity, MainActivity::class.java)
            intent.putExtra("ACTION", action)
            intent.putExtra("INDEX", index)
            intent.putExtra("FIRST", editTextFirstName.text.toString())
            intent.putExtra("SECOND", editTextSecondName.text.toString())
            intent.putExtra("MIDDLE", editTextMiddleName.text.toString())
            intent.putExtra("BIRTH", editTextBirthDay.text.toString())
            intent.putExtra("FACULTY", editTextFaculty.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}