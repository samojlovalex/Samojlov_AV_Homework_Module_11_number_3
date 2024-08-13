package com.example.samojlov_av_homework_module_11_number_3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_11_number_3.databinding.ActivitySecondBinding

@Suppress("DEPRECATION")
class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var secondPersonOutTextViewTV: TextView
    private lateinit var buttonSecondBackBT: Button
    private lateinit var buttonSecondEditBT: Button
    private lateinit var buttonSecondClearBT: Button
    var person: Person? = null
    var position: String? = null
    var userList: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }
    @SuppressLint("SetTextI18n")
    private fun init() {
        secondPersonOutTextViewTV = binding.secondPersonOutTextViewTV
        buttonSecondBackBT = binding.buttonSecondBackBT
        buttonSecondEditBT = binding.buttonSecondEditBT
        buttonSecondClearBT = binding.buttonSecondClearBT
        person = intent.extras?.getParcelable(Person::class.java.simpleName) as Person?
        position = intent.getStringExtra("positionOut")
        userList = intent.getStringExtra("userListOut")


        secondPersonOutTextViewTV.text = person.toString()

        buttonSecondBackBT.setOnClickListener(this)
        buttonSecondEditBT.setOnClickListener (this)
        buttonSecondClearBT.setOnClickListener (this)

    }

    override fun onClick(v: View?) {
        val result = when (v?.id){
            R.id.buttonSecondBackBT -> "0"
            R.id.buttonSecondEditBT -> "1"
            R.id.buttonSecondClearBT -> "2"
            else -> ""
        }
        val intent = Intent()
        intent.putExtra("secondButton",result)
        intent.putExtra("listPosition", position)
        intent.putExtra("userListBack", userList)
        setResult(RESULT_OK, intent)
        finish()
    }

}