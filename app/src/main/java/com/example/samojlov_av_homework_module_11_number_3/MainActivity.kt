package com.example.samojlov_av_homework_module_11_number_3

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_11_number_3.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var editTextMainNameET: EditText
    private lateinit var editTextSurnameET: EditText
    private lateinit var editTextMainPhone: EditText
    private lateinit var editTextMainAddress: EditText
    private lateinit var buttonMainSaveBT: Button
    private lateinit var buttonMainClearBT: Button
    private lateinit var listViewMainLV: ListView

    private var personList: MutableList<Person> = mutableListOf()
    private var listVieW: MutableList<String> = mutableListOf()
    private var secondActivityButtonItem: String? = null
    private var positionListView: Int? = null
    private var listBack: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {

        editTextMainNameET = binding.editTextMainNameET
        editTextSurnameET = binding.editTextSurnameET
        editTextMainPhone = binding.editTextMainPhone
        editTextMainAddress = binding.editTextMainAddress
        buttonMainSaveBT = binding.buttonMainSaveBT
        buttonMainClearBT = binding.buttonMainClearBT
        listViewMainLV = binding.listViewMainLV

        programWork()
    }

    private fun programWork() {
        buttonMainClearBT.setOnClickListener {
            clearingFields()
        }

        buttonMainSaveBT.setOnClickListener {
            savePerson()

        }

        listViewMainLV.onItemClickListener =
            listVieWCheck()
    }

    private fun clearingFields() {
        editTextMainNameET.text.clear()
        editTextSurnameET.text.clear()
        editTextMainPhone.text.clear()
        editTextMainAddress.text.clear()
        Snackbar.make(
            buttonMainClearBT,
            getString(R.string.buttonMainClearBTMessage), Snackbar.LENGTH_LONG
        ).show()
    }

    private fun secondActivityBack() {

        val position = positionListView!!
        val type = typeOf<MutableList<Person>>().javaType
        val gson: MutableList<Person> = Gson().fromJson(listBack, type)

        val adapter = ArrayAdapter(this, R.layout.mytextview, listVieW)
        listViewMainLV.adapter = adapter

        personList.clear()
        listVieW.clear()
        personList = gson

        when (secondActivityButtonItem) {
            "0" -> {
                recalculationListView()
                adapter.notifyDataSetChanged()
            }

            "1" -> {
                editTextMainNameET.setText(personList[position].name)
                editTextSurnameET.setText(personList[position].surname)
                editTextMainPhone.setText(personList[position].phone)
                editTextMainAddress.setText(personList[position].address)
                personList.remove(personList[position])
                recalculationListView()
                adapter.notifyDataSetChanged()
            }

            "2" -> {
                personList.remove(personList[position])
                recalculationListView()
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun arrayAdapter(): ArrayAdapter<String> {
        val adapter = ArrayAdapter(this, R.layout.mytextview, listVieW)
        return adapter
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun listVieWCheck() =
        AdapterView.OnItemClickListener { _, _, position, _ ->
            val position = position
            val type = typeOf<MutableList<Person>>().javaType
            val gson = Gson().toJson(personList, type)
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(
                Person::class.java.simpleName,
                personList[position]
            )
            intent.putExtra("positionOut", position.toString())
            intent.putExtra("userListOut", gson)
            launchSecondActivityActivity.launch(intent)
        }

    private fun savePerson() {
        if (editTextMainNameET.text.isEmpty() || editTextSurnameET.text.isEmpty()) {
            Snackbar.make(
                buttonMainSaveBT,
                getString(R.string.buttonMainSaveBTMessageOne),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        if (editTextMainNameET.text.length <= 1 || editTextSurnameET.text.length <= 1) {
            Snackbar.make(
                buttonMainSaveBT,
                getString(R.string.buttonMainSaveBTMessageTwo),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val adapter = ArrayAdapter(this, R.layout.mytextview, listVieW)
        listViewMainLV.adapter = adapter

        val person = Person(
            editTextMainNameET.text.toString(),
            editTextSurnameET.text.toString(),
            editTextMainPhone.text.toString(),
            editTextMainAddress.text.toString()
        )
        personList.add(person)
        listVieW.clear()
        recalculationListView()
        adapter.notifyDataSetChanged()
        editTextMainNameET.text.clear()
        editTextSurnameET.text.clear()
        editTextMainPhone.text.clear()
        editTextMainAddress.text.clear()
    }

    private fun recalculationListView() {
        listVieW.clear()
        for (i in personList.indices) {
            listVieW.add("${personList[i].name} ${personList[i].surname}")
        }
    }


    private val launchSecondActivityActivity =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val button = data!!.getStringExtra("secondButton").toString()
                val position = data!!.getStringExtra("listPosition")?.toInt()
                val list = data!!.getStringExtra("userListBack")
                secondActivityButtonItem = button
                positionListView = position
                listBack = list
                secondActivityBack()
            } else {
                Toast.makeText(this, "Canceled", Toast.LENGTH_LONG)
            }
        }
}