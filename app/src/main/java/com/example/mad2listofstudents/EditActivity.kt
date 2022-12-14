package com.example.mad2listofstudents

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat

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

        if (action == 2)
        {
            editTextFirstName.setText(intent.getSerializableExtra("FIRST") as String)
            editTextSecondName.setText(intent.getSerializableExtra("SECOND") as String)
            editTextMiddleName.setText(intent.getSerializableExtra("MIDDLE") as String)
            editTextBirthDay.setText(intent.getSerializableExtra("BIRTH") as String)
            editTextFaculty.setText(intent.getSerializableExtra("FACULTY") as String)
        }
    }

    private fun confirmChanges(action: Int, index: Int)
    {
        if (editTextFirstName.text.toString() != "" && editTextSecondName.text.toString() != ""
            && editTextMiddleName.text.toString() != "" && editTextBirthDay.text.toString() != ""
            && editTextFaculty.text.toString() != "")
        {
            if (isDateValid(editTextBirthDay.text.toString()))
            {
                val intent = Intent(this@EditActivity, MainActivity::class.java)
                intent.putExtra("ACTION", action)
                intent.putExtra("INDEX", index)
                intent.putExtra("FIRST", editTextFirstName.text.toString().trim())
                intent.putExtra("SECOND", editTextSecondName.text.toString().trim())
                intent.putExtra("MIDDLE", editTextMiddleName.text.toString().trim())
                intent.putExtra("BIRTH", editTextBirthDay.text.toString().trim())
                intent.putExtra("FACULTY", editTextFaculty.text.toString().trim())
                setResult(RESULT_OK, intent)
                finish()
            }
            else
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "?????????????? ???????????????????? ????????!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        else
        {
            val toast = Toast.makeText(
                applicationContext,
                "?????????????????? ?????? ????????!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun isDateValid(date: String?): Boolean
    {
        val myFormat = SimpleDateFormat("dd.MM.yyyy")
        myFormat.isLenient = false
        return try
        {
            if (date != null)
            {
                myFormat.parse(date)
            }
            true
        }
        catch (e: Exception)
        {
            false
        }
    }
}