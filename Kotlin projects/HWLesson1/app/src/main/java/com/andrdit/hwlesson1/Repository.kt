package com.andrdit.hwlesson1

object Repository {

    private val personList: List<Person> =
        listOf(
        Person("NoName из репозитория"),
        Person("Фредон из репозитория", "Петрович"),
        Person("Андрей из репозитория", "Дитковский"))

    fun getPersonList(): List<Person> {
        return personList
    }


}