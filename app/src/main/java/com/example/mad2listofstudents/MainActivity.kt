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

    private lateinit var buttonSNext: Button
    private lateinit var buttonSPrev: Button

    private lateinit var buttonFNext: Button
    private lateinit var buttonFPrev: Button

    private val u = University()

    private var indexOfStudents: Int = -1
    private var indexOfFaculty: Int = -1

    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCurF = findViewById(R.id.textViewCurF)
        textViewCurS = findViewById(R.id.textViewCurS)

        buttonSNext = findViewById(R.id.buttonNextS)
        buttonSPrev = findViewById(R.id.buttonPrevS)

        buttonFNext = findViewById(R.id.buttonNextF)
        buttonFPrev = findViewById(R.id.buttonPrevF)

        findViewById<Button>(R.id.buttonPrevF).setOnClickListener { facListing(false) }
        findViewById<Button>(R.id.buttonNextF).setOnClickListener { facListing(true) }

        findViewById<Button>(R.id.buttonPrevS).setOnClickListener { studentListing(false) }
        findViewById<Button>(R.id.buttonNextS).setOnClickListener { studentListing(true) }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener { editListOfStudents(1) }
        findViewById<Button>(R.id.buttonEdit).setOnClickListener { editListOfStudents(2) }
        findViewById<Button>(R.id.buttonDel).setOnClickListener { editListOfStudents(3) }

        textViewCurF.text = "Все факультеты"

        toast = Toast.makeText(
            applicationContext,
            "Добавьте студентов!",
            Toast.LENGTH_SHORT
        )

        refresh()
    }

    private fun facListing(isNext: Boolean)
    {
        if (u.getFaculties().isEmpty())
        {
            toast.show()
        }
        else
        {
            if (isNext)
            {
                if (indexOfFaculty + 1 < u.getFaculties().size)
                {
                    indexOfFaculty++
                }
            }
            else if (indexOfFaculty - 1 > -2)
            {
                indexOfFaculty--
            }
            if (indexOfFaculty != -1)
            {
                for (i in 0 until u.getStudents().size)
                {
                    if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                    {
                        indexOfStudents = i
                        break
                    }
                }
            }
            refresh()
        }
    }

    private fun studentListing(isNext: Boolean)
    {
        if (u.getStudents().isEmpty())
        {
            toast.show()
        }
        else
        {
            if (isNext)
            {
                if (indexOfStudents + 1 < u.getStudents().size)
                {
                    if (indexOfFaculty == -1)
                    {
                        indexOfStudents++
                    }
                    else
                    {
                        for (i in indexOfStudents + 1 until u.getStudents().size)
                        {
                            if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                            {
                                indexOfStudents = i
                                break
                            }
                        }
                    }
                }
            }
            else
            {
                if (indexOfStudents - 1 > -1)
                {
                    if (indexOfFaculty == -1)
                    {
                        indexOfStudents--
                    }
                    else
                    {
                        for (i in indexOfStudents - 1 downTo 0)
                        {
                            if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                            {
                                indexOfStudents = i
                                break
                            }
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
                toast.show()
            }
            else
            {
                val intent = Intent()
                intent.setClass(this, EditActivity::class.java)
                intent.putExtra("ACTION", action)
                intent.putExtra("INDEX", indexOfStudents)
                if (action == 2)
                {
                    intent.putExtra("FIRST", u.getStudents()[indexOfStudents].firstName)
                    intent.putExtra("SECOND", u.getStudents()[indexOfStudents].secondName)
                    intent.putExtra("MIDDLE", u.getStudents()[indexOfStudents].middleName)
                    intent.putExtra("BIRTH", u.getStudents()[indexOfStudents].birthDay)
                    intent.putExtra("FACULTY", u.getStudents()[indexOfStudents].faculty)
                }
                startActivityForResult(intent, 1)
            }
        }
        if (action == 3)
        {
            if (u.getStudents().isNotEmpty())
            {
                u.delStudent(indexOfStudents)
                indexOfFaculty = -1
                indexOfStudents = -1
                refresh()
            }
            else
            {
                toast.show()
            }
        }
    }

    private fun refresh()
    {
        if (indexOfStudents == -1 && u.getStudents().isNotEmpty())
        {
            indexOfStudents = 0
        }

        if (indexOfFaculty != -1)
        {
            textViewCurF.text = u.getFaculties()[indexOfFaculty]
        }
        if (indexOfStudents != -1)
        {
            val tempString: String = (u.getStudents()[indexOfStudents].firstName + " "
                    + u.getStudents()[indexOfStudents].secondName + " "
                    + u.getStudents()[indexOfStudents].middleName + ", "
                    + u.getStudents()[indexOfStudents].birthDay + ", "
                    + u.getStudents()[indexOfStudents].faculty)
            textViewCurS.text = tempString
        }

        if (indexOfFaculty == -1)
        {
            textViewCurF.text = "Все факультеты"
        }
        if (indexOfStudents == -1)
        {
            textViewCurS.text = "Нет студентов"
        }

        if (indexOfFaculty == -1)
        {
            buttonFPrev.isEnabled = false
            buttonFNext.isEnabled = u.getFaculties().size != 0
            if (indexOfStudents == -1)
            {
                buttonSPrev.isEnabled = false
                buttonSNext.isEnabled = false
            }
            else
            {
                buttonSPrev.isEnabled = indexOfStudents > 0
                buttonSNext.isEnabled = indexOfStudents < u.getStudents().size - 1
            }
        }
        else
        {
            buttonFPrev.isEnabled = true
            var tempBoolean = false
            for (i in indexOfStudents + 1 until u.getStudents().size)
            {
                if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                {
                    tempBoolean = true
                    break
                }
            }
            buttonSNext.isEnabled = tempBoolean
            tempBoolean = false
            for (i in indexOfStudents - 1 downTo 0)
            {
                if (u.getStudents()[i].faculty == u.getFaculties()[indexOfFaculty])
                {
                    tempBoolean = true
                    break
                }
            }
            buttonSPrev.isEnabled = tempBoolean
        }
        buttonFNext.isEnabled = indexOfFaculty < u.getFaculties().size - 1
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