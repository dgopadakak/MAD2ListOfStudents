package com.example.mad2listofstudents

class University
{
    private var listOfStudents: ArrayList<Student> = ArrayList()
    private val listOfFaculties: ArrayList<String> = ArrayList()

    fun getStudents(): ArrayList<Student>
    {
        return listOfStudents
    }

    fun getFaculties(): ArrayList<String>
    {
        return listOfFaculties
    }

    fun addStudent(student: Student)
    {
        listOfStudents.add(student)
        var isNewFaculty = true
        for (faculty in listOfFaculties)
        {
            if (student.faculty == faculty)
            {
                isNewFaculty = false
            }
        }
        if (isNewFaculty)
        {
            listOfFaculties.add(student.faculty)
        }
        sortAlphabetically()
    }

    fun delStudent(index: Int)
    {
        var numOfStudentsInFaculty = 0
        for (faculty in listOfFaculties)
        {
            if (faculty == listOfStudents[index].faculty)
            {
                numOfStudentsInFaculty++
            }
        }
        if (numOfStudentsInFaculty == 1)
        {
            listOfFaculties.remove(listOfStudents[index].faculty)
        }
        listOfStudents.removeAt(index)
    }

    fun editStudent(index: Int, newStudentInfo: Student)
    {
        this.delStudent(index)
        this.addStudent(newStudentInfo)
        sortAlphabetically()
    }

    private fun sortAlphabetically()
    {
        listOfFaculties.sort()

        val tempListOfNames: ArrayList<String> = ArrayList()
        for (i in listOfStudents)
        {
            tempListOfNames.add(i.firstName)
        }
        tempListOfNames.sort()
        for (i in 0 until tempListOfNames.size)
        {
            for (j in 0 until listOfStudents.size)
            {
                if (listOfStudents[j].firstName == tempListOfNames[i])
                {
                    val tempStudent = Student(listOfStudents[i].firstName
                        , listOfStudents[i].secondName, listOfStudents[i].middleName
                        , listOfStudents[i].birthDay, listOfStudents[i].faculty)
                    listOfStudents[i] = listOfStudents[j]
                    listOfStudents[j] = tempStudent
                    break
                }
            }
        }
    }
}