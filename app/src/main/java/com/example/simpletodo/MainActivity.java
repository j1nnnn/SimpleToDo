package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    // Detecting when the user clicks on add button
    Button mButton;

    List<String> listOfTasks = new ArrayList<>();
    TaskItemAdapter adapter;
    TaskItemAdapter.OnLongClickListener blongClickListener = new TaskItemAdapter.OnLongClickListener() {
        @Override
        public void onItemLongClicked(int adapterPosition) {
            // Remove the item from the list
            listOfTasks.remove(adapterPosition);

            // Notify the adapter that our data set has changed
            adapter.notifyDataSetChanged();

            saveItems();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadItems();
        
        // Look up recyclerView in layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView); // iffy
        // Create adapter passing in the sample user data
        adapter = new TaskItemAdapter(listOfTasks, blongClickListener);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the button and input field, so that he user can enter a task and add it to the list

        // Get a reference to the button
        // and then set an onclicklistener
        mButton = (Button)findViewById(R.id.button);
        EditText inputTextField = (EditText) findViewById(R.id.addTaskField);

        mButton.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    // actions when button is clicked
                    Log.v("Caren", "User clicked on button");

                    //* 1. Grab the text the user has inputted into @id/addTaskField *//

                    // EditText userInputtedTask = (EditText) findViewById(R.id.addTaskField);
                    // Then converting into a string
                    String userTask = inputTextField.getText().toString();

                    //* 2. Add the string to our list of tasks: listOfTasks *//

                    listOfTasks.add(userTask);
                    // Notify the adapter that our data has been updated
                    adapter.notifyItemInserted(listOfTasks.size() - 1);

                    //* 3. Reset text field *//

                    inputTextField.setText("");

                    saveItems();
                }
            }
        );
    }

    // Save the data that the user has inputted
    // Save data by writing and reading from a file

    // Get the file we need
    File getDataFile() {

        // Every line is going to represent a specific task in our list of tasks
        return new File(getFilesDir(), "data.txt");
    }

    // Load the items by reading every line in the data file
    public void loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    // Save items by writing them into our data file
    public void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}