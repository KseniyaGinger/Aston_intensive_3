package ru.aston.aston_intensive_3

import com.github.javafaker.Faker


typealias PersonListener = (persons: List<Person>) -> Unit

class PersonService {

    private var person = mutableListOf<Person>()
    private var listeners = mutableListOf<PersonListener>()

    init {

        val faker = Faker.instance()

        person = (1..101).map {
            Person(
                id = it.toLong(),
                name = faker.name().firstName(),
                phoneNumber = faker.phoneNumber().cellPhone()
            )
        }.toMutableList()
    }

    fun getPersons(): List<Person> = person

    fun removePerson(targetPerson: Person) {
        val index = person.indexOfFirst { it.id == targetPerson.id }
        if (index == -1) return

        person = ArrayList(person)
        person.removeAt(index)
        notifyChanges()
    }

    fun addListener(listener: PersonListener) {
        listeners.add(listener)
        listener.invoke(person)
    }

    fun removeListener(listener: PersonListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() = listeners.forEach { it.invoke(person) }
}