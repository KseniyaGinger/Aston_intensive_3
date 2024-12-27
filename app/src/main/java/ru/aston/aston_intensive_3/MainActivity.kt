package ru.aston.aston_intensive_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.aston.aston_intensive_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private val personService: PersonService
        get() = (applicationContext as App).personService
    private val listener: PersonListener = { adapter.data = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)

        adapter = PersonAdapter(object : ContactActionListener {
            override fun onPersonGetId(person: Person) =
                Toast.makeText(this@MainActivity, "Persons ID: ${person.id}", Toast.LENGTH_SHORT)
                    .show()

            override fun onPersonRemove(person: Person) = personService.removePerson(person)

        })
        adapter.data = personService.getPersons()
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        personService.addListener(listener)
    }
}