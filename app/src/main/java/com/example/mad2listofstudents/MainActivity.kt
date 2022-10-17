package com.example.mad2listofstudents

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity()
{
    private lateinit var textViewCurF: TextView
    private lateinit var textViewCurS: TextView

    private val u = University()

    private var indexOfStudents: Int = -1
    private var indexOfFaculty: Int = -1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCurF = findViewById(R.id.textViewCurF)
        textViewCurS = findViewById(R.id.textViewCurS)

        findViewById<Button>(R.id.buttonPrevF).setOnClickListener { facListing(false) }
        findViewById<Button>(R.id.buttonNextF).setOnClickListener { facListing(true) }

        findViewById<Button>(R.id.buttonPrevS).setOnClickListener { studentListing(false) }
        findViewById<Button>(R.id.buttonNextS).setOnClickListener { studentListing(true) }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener { editListOfStudents(1) }
        findViewById<Button>(R.id.buttonEdit).setOnClickListener { editListOfStudents(2) }
        findViewById<Button>(R.id.buttonDel).setOnClickListener { editListOfStudents(3) }

        textViewCurF.text = "Все факультеты"
    }

    private fun facListing(isNext: Boolean)
    {
        if (u.getFaculties().isEmpty())
        {
            val toast = Toast.makeText(
                applicationContext,
                "Добавьте студентов!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
        else
        {
            if (isNext && indexOfFaculty + 1 < u.getFaculties().size)
            {
                indexOfFaculty++
            }
            else if (indexOfFaculty - 1 > -2)
            {
                indexOfFaculty--
            }
            refresh()
        }
    }

    private fun studentListing(isNext: Boolean)
    {
        if (u.getStudents().isEmpty())
        {
            val toast = Toast.makeText(
                applicationContext,
                "Добавьте студентов!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
        else
        {
            if (isNext && indexOfStudents + 1 < u.getStudents().size)
            {
                if (indexOfFaculty == -1)
                {
                    indexOfStudents++
                }
                else
                {
                    for (i in indexOfStudents + 1..u.getStudents().size)
                    {
                        if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                        {
                            indexOfStudents = i
                            break
                        }
                    }
                }
            }
            else
            {
                if (indexOfFaculty == -1 && indexOfStudents - 1 > -1)
                {
                    indexOfStudents--
                }
                else
                {
                    for (i in indexOfStudents - 1 downTo -1)
                    {
                        if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                        {
                            indexOfStudents = i
                            break
                        }
                    }
                }
            }
            refresh()
        }
    }

    private fun editListOfStudents(action: Int)
    {
        if (action == 1 || action == 2)
        {
            if (action == 2 && u.getStudents().isEmpty())
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Добавьте студентов!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
            else
            {
                val intent = Intent()
                intent.setClass(this, EditActivity::class.java)
                intent.putExtra("ACTION", action)
                intent.putExtra("INDEX", indexOfStudents)
                startActivityForResult(intent, 1)
            }
        }
        if (action == 3)
        {

        }
    }

    private fun refresh()
    {
        if (indexOfFaculty == -1)
        {
            textViewCurF.text = "Все факультеты"
        }
        if (indexOfStudents == -1)
        {
            textViewCurS.text = "Добавьте студентов"
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
        {
            var action = data?.getSerializableExtra("ACTION")
            var index = data?.getSerializableExtra("INDEX")
            val firstName = data?.getSerializableExtra("FIRST")
            val secondName = data?.getSerializableExtra("SECOND")
            val middleName = data?.getSerializableExtra("MIDDLE")
            val birthDay = data?.getSerializableExtra("BIRTH")
            val faculty = data?.getSerializableExtra("FACULTY")
            action = action as Int
            index = index as Int
            val student = Student(firstName as String, secondName as String
                , middleName as String, birthDay as String, faculty as String)
            if (action == 1)
            {
                u.addStudent(student)
                refresh()
            }
            else
            {
                u.editStudent(index, student)
                refresh()
            }
        }
    }
}