// MINI-PROJECT (TO-DO TASK)
// MADE BY: PARAS VARSHNEY
// MOB: 7358337024

package com.example.paras.todotasks;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {       // Main Activity.

    // making objects for the following.
    private static DatabaseAdapter databaseAdapter;
    ListView listView;
    List<MyTask> myTaskList;
    MyListAdapter adapter;
    public static String editTitle22;
    public static String editDescription22;
    public static String editDate22;
    private static long back_pressed;


    ////////////////////////////////////////////////////////////////////////////////////////////////////   onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //set the visible xml layout of the main activity
        databaseAdapter = new DatabaseAdapter(this);    //create an object of DatabaseAdapter class created earlier.
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();  //get an instance of database of type SQLiteDatabase
        listView = (ListView) findViewById(R.id.listViewMain);
        selectDataOfUndoneItems();

        ////////////////////////////////on list item clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                editTitle22 = myTaskList.get(i).getTitle();
                editDescription22 = myTaskList.get(i).getDescription();
                editDate22 = myTaskList.get(i).getDate();


                UpdateDialogBox updateDialogBox = new UpdateDialogBox();
                updateDialogBox.setContext(getApplicationContext());
                updateDialogBox.show(getSupportFragmentManager(), "Todo_Dialog2");
                updateDialogBox.makeEditTextPopulated(editTitle22, editDescription22, editDate22);


                Toast.makeText(MainActivity.this, "" + editTitle22 + " " + editDescription22, Toast.LENGTH_SHORT).show();
            }
        });

        //////////////////////////////////on list item long clicked
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //calling the method to update the status to 1 (item completed) of the item in listView on a long click
                updateStatusOfTask(i);
                return true;
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////  ends onCreate
//rpogh

    ////////////////////////////////////////////////////////////////////////////////////////////////////   Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // function for creating an option menu.
        MenuInflater menuInf = getMenuInflater();      // getting the menu inflater's instance.
        menuInf.inflate(R.menu.menu_layout, menu);       //inflating the menu with the layout file created separately.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // function to do something when an item is selected in Option Menu.
        int id = item.getItemId();      // storing the id of item clicked in the "id" variable.
        switch (id) {
            case R.id.addNewTask:   // if the "plus mark" is clicked then..
                //todo open the dialog box here.
                DialogBox dialogBox = new DialogBox();      // creating the object of DialogBox class.
                dialogBox.setContext(this);     // setting the context of dialog box as the same as this class.
                dialogBox.show(getSupportFragmentManager(), "Todo_Dialog");      // display the dialog box.

                break;
            case R.id.showCompletedTask:        // if the "thumb mark" is clicked then..
                //todo open the activity where the completed tasks are shown.
                // making an intent to go to CompleteTask class from this class.
                Intent intent = new Intent(MainActivity.this, CompletedTasks.class);
                startActivity(intent);      //starting the intent.
                Toast.makeText(getApplicationContext(), "Completed Tasks", Toast.LENGTH_SHORT).show();  // toast to display the task is completed.
                break;

        }
        return super.onOptionsItemSelected(item);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  Ends Menu


    ////////////////////////////////////////////////////////////////////////////////////////////////////  insert Data
// function to insert title, description, date in the database.
    public static void insertData(String title, String description, String date) {
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();  //get an instance of database of type SQLiteDatabase
        ContentValues cv = new ContentValues();
        cv.put(DatabaseAdapter.TITLE, title);
        cv.put(DatabaseAdapter.DESCRIPTION, description);
        cv.put(DatabaseAdapter.DATE, date);
        db.insert(DatabaseAdapter.TABLE_NAME, null, cv);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends insert Data

    ////////////////////////////////////////////////////////////////////////////////////////////////////  update Data
    public static void updateData(String newTitle, String newDescription, String newDate) {
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();  //get an instance of database of type SQLiteDatabase
        ContentValues cv = new ContentValues();     // creating object of content values.
        cv.put(DatabaseAdapter.TITLE, newTitle);    //put the new title, description, date values in the content values.
        cv.put(DatabaseAdapter.DESCRIPTION, newDescription);
        cv.put(DatabaseAdapter.DATE, newDate);
        String[] whereArgs = {editTitle22};     // a string array to store the there arguments for the query.
        db.update(DatabaseAdapter.TABLE_NAME, cv, DatabaseAdapter.TITLE + " =? ", whereArgs);  // update the query with the title name as the criteria for updation.
        db.close();     //close the database connection to SQLite.
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends up Data

    ////////////////////////////////////////////////////////////////////////////////////////////////////   select data
    // a method to select the data from the database that is not completed (staus = 0)
    public void selectDataOfUndoneItems() {

        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();  //get an instance of database of type SQLiteDatabase
        // a string array to store the column names that are being selected.
        String[] column = {DatabaseAdapter.TITLE, DatabaseAdapter.DESCRIPTION, DatabaseAdapter.DATE, DatabaseAdapter.STATUS};
        // cursor object to point to the specific column in the column array.
        Cursor cursor = sqLiteDatabase.query(DatabaseAdapter.TABLE_NAME, column, null, null, null, null, null);

        myTaskList = new ArrayList<>();     // creating an object of the ArrayList.
        // storing the indices of the respective columns in variables.
        int i1 = cursor.getColumnIndex(DatabaseAdapter.TITLE);
        int i2 = cursor.getColumnIndex(DatabaseAdapter.DESCRIPTION);
        int i3 = cursor.getColumnIndex(DatabaseAdapter.DATE);
        int i4 = cursor.getColumnIndex(DatabaseAdapter.STATUS);


        cursor.moveToFirst();       // positioning the cursor to the first row.
        cursor.moveToPrevious();       // go a step back
        while (cursor.moveToNext()) {       // while loop to keep moving to the next row until it finishes traversing all rows.
            if (cursor.getInt(i4) == 0) {     // if status = 0 then..
                // store the string at the cursor position in the string variables.
                String title = cursor.getString(i1);
                String description = cursor.getString(i2);
                String date = cursor.getString(i3);
                int status = cursor.getInt(i4);
                //add the title, description, date, status in the layout created.

                myTaskList.add(new MyTask(R.drawable.uncompleted, title, description, date, status));
            }
        }
        // i use collections to sort the list by due date and the sorting algorithm i have applied in MyTask.java
        Collections.sort(myTaskList, new SortByDate());

                // add the listView layout to the adapter.
        adapter = new MyListAdapter(DatabaseAdapter.context, R.layout.list_view_layout, myTaskList);
        listView.setAdapter(adapter);   // populate the adapter values in the listView.
        sqLiteDatabase.close();     //close the database connection to SQLite.
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends select data

    ////////////////////////////////////////////////////////////////////////////////////////////////////    update status
    // a method to update the status of the task in the list view.
    // this method is called when we long click the list view item in the uncompleted task listView.
    public void updateStatusOfTask(int i) {

        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();  //get an instance of database of type SQLiteDatabase
        String titleName = (String) myTaskList.get(i).getTitle();    // store the title at the i'th index in a variable.
        // display a message to user that the task is finished.
        Toast.makeText(MainActivity.this, "Congratulations on finishing " + titleName, Toast.LENGTH_SHORT).show();

        // create a object of the content view class.
        ContentValues cv2 = new ContentValues();
        cv2.put(DatabaseAdapter.STATUS, 1);     // put the value of status = 1 in the content values.
        String[] whereArgs = {titleName};   // a string array that stores the title name
        // update the the staus value in the database where the title name is the same as the title at this index.
        sqLiteDatabase.update(DatabaseAdapter.TABLE_NAME, cv2, DatabaseAdapter.TITLE + " =? ", whereArgs);
        sqLiteDatabase.close();     //close the database connection to SQLite.
        Intent intent = new Intent(MainActivity.this, MainActivity.class);   // intent to the same activity to refresh the page.
        startActivity(intent);      // start activity.
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends update status

    // method for on Back press override, user need to press twice back to exit.
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {     // wait for 2 seconds until the back button is pressed again.
            super.onBackPressed();
            Toast.makeText(getBaseContext(), "Thank you for using my App!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}

