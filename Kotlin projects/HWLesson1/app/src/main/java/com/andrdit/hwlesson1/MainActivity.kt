package com.andrdit.hwlesson1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etSoname: EditText
    lateinit var btSetText: Button
    lateinit var personList: List<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etSoname = findViewById(R.id.etSoname)
        btSetText = findViewById(R.id.btSetText)

        val person1 = Person()
        val person2 = Person("Фредон", "Петрович")
        val person3 = Person("Андрей", "Дитковский")

        personList = listOf(person1, person2, person3)

        val clickListener: View.OnClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.btSetText -> {
                    for(person in personList) {
                        setText(person)
                        var a: Int = 0;

                        // как сделать задержку вывода не знаю :(
                        for(i in 1..10000) {
                            a = a + i;
                        }
                    }
                }
            }
        }
        btSetText.setOnClickListener(clickListener)

        // работа с копией
        val person3Copy = person3.copy()

        Toast.makeText(applicationContext, "Вывод из копии объекта: " + person3Copy.name + " " + person3Copy.soname, Toast.LENGTH_LONG).show()
    }

    fun setText(person: Person){

        for(person in personList) {
            //Toast.makeText(applicationContext, person.name + " " + person.soname, Toast.LENGTH_LONG).show()
            etName.setText(person.name)
            etSoname.setText(person.soname)
        }
    }
}