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
        var isNewFaculty: Boolean = true
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
    }

    fun delStudent(index: Int)
    {
        var numOfStudentsInFaculty: Int = 0
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
    }
}