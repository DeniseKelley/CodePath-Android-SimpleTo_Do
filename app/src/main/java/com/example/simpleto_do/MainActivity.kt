package com.example.simpleto_do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()

    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. remove the item
                listOfTasks.removeAt(position)
                //2. notify the adapter
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }

        //1. Let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            //this code executes when the user clicks on the button
//            Log.i("Caren", "User on button")
//        }

       // listOfTasks.add("Do Laundry")
       // listOfTasks.add("Go for a walk")

        loadItems()


        val recycleView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this)
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Set up the button and user input field
        findViewById<Button>(R.id.button).setOnClickListener{
            //grab the text from the user
            val userInputtedTask = inputTextField.text.toString()
            //add the string to the list
            listOfTasks.add(userInputtedTask)
            //notify the adapter
            adapter.notifyItemInserted(listOfTasks.size-1)
            //reset text field
            inputTextField.setText("")

            saveItems()


        }
    }
    //get the file
    fun getDataFile(): File {
        //every line represents a task
        return File(filesDir, "data.txt")
    }
    //save the data that the user has inputed

    //create a method to get the file

    //load the items by reading every line in the data
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException)
        {
            ioException.printStackTrace()
        }
    }

    //save the items writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}