package com.example.test_kotlin_09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val runButton: Button by lazy{
        findViewById(R.id.runButton)
    }
    private val addButton: Button by lazy{
        findViewById(R.id.addButton)
    }
    private val clearButton: Button by lazy{
        findViewById<Button>(R.id.clearButton)
    }
    private val numberPicker:NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }

    private val numberTextViewlist: List<TextView> by lazy{
        listOf(
            findViewById(R.id.First_NumTv),
            findViewById(R.id.Second_NumTv),
            findViewById(R.id.Thrid_NumTv),
            findViewById(R.id.Fourth_NumTv),
            findViewById(R.id.Fifth_NumTv),
            findViewById(R.id.Six_NumTv)
        )
    }
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initClearButton()
        initRunButton()
        initAddButton()

    }
    private fun initClearButton(){
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewlist.forEach{
                it.isVisible = false
            }
            didRun = false
        }
    }


    private fun initRunButton(){
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number->
                val textView = numberTextViewlist[index]

                textView.text = number.toString()
                textView.isVisible = true
                setNumberBackground(index,textView)
            }


            Log.d("MainActivity",list.toString())
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this,"초기화 후에 시도하세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5){
                Toast.makeText(this,"번호는 5개까지만 선택할 수 있습니다. ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택한 번호입니다. ",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewlist[pickNumberSet.size]
            textView.isVisible =true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value,textView)

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun setNumberBackground(number:Int,textView: TextView){
        when(number){
            in 1..10 -> textView.background =ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 11..20 -> textView.background =ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background =ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 31..40 -> textView.background =ContextCompat.getDrawable(this,R.drawable.circle_blue)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
        }
    }

    private fun getRandomNumber(): List<Int>{
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45){
                    if(pickNumberSet.contains(i)) continue
                    this.add(i)
                }
            }
        numberList.shuffle()
        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted()
    }

}