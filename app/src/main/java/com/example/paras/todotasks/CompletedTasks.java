package com.example.paras.todotasks;

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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// a class CompletedTasks that extends AppCompatActivity class
public class CompletedTasks extends AppCompatActivity {

    // object initialization
    private static DatabaseAdapter databaseAdapter;
    List<MyTask> myTaskList;
    MyListAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {        //onCreate method initialization overridden by AppCompatActivity class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);      // inflating the activity_completed_tasks layout.
        databaseAdapter = new DatabaseAdapter(this);    // creating the object of DatabaseAdapter class.
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();  // creating writable database instance of the SQLiteDatabase

        listView = (ListView) findViewById(R.id.listViewColpeted);  // storing the listview instance by finding view by id from xml file.
        selectDataOfDoneItems();    // calling method to select the item which are done in the completed tasks.


        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {      // on clicking the item of the list view.
                String titleName = (String) myTaskList.get(i).getTitle();   // storing in the variable the Title at the i'th position.
                deleteFromDatabase(titleName);  // calling the function that delete the details corrosponding to the title name.
                // refreshing the page.
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////   Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // method on creating the menu.
        MenuInflater menuInf = getMenuInflater();   // menu inflater class instance.
        menuInf.inflate(R.menu.completed_tasks_menu_layout,menu);   // inflating the instance with the layout file.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // method on selecting the item in the menu.
        int id = item.getItemId();  // getting the id of the item selected.
        switch (id){

            case R.id.showIncompleteTask:   // if the incomplete task thumb is clicked then..
                // intent from complete task to the main activity class.
                Intent intent = new Intent(CompletedTasks.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Incomplete Tasks", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  Ends Menu

////////////////////////////////////////////////////////////////////////////////////////////////////   select data
    public void selectDataOfDoneItems(){    // select data method for showing the done items.
// this method is similar as the one in main activity only difference is that the status =1 then select the data.
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();
        String[] column = {DatabaseAdapter.TITLE, DatabaseAdapter.DESCRIPTION, DatabaseAdapter.DATE, DatabaseAdapter.STATUS};
        Cursor cursor = sqLiteDatabase.query(DatabaseAdapter.TABLE_NAME, column, null, null, null, null,null);

        myTaskList = new ArrayList<>();
        int i1 = cursor.getColumnIndex(DatabaseAdapter.TITLE);
        int i2 = cursor.getColumnIndex(DatabaseAdapter.DESCRIPTION);
        int i3 = cursor.getColumnIndex(DatabaseAdapter.DATE);
        int i4 = cursor.getColumnIndex(DatabaseAdapter.STATUS);
        cursor.moveToFirst();
        cursor.moveToPrevious();
        while (cursor.moveToNext()){
            if (cursor.getInt(i4)== 1){
                String title = cursor.getString(i1);
                String description= cursor.getString(i2);
                String date = cursor.getString(i3);
                int status = cursor.getInt(i4);
                myTaskList.add(new MyTask(R.drawable.completed, title, description, date, status));
            }
        }
        Collections.sort(myTaskList, new SortByDate());
        adapter = new MyListAdapter(DatabaseAdapter.context, R.layout.completed_list_view_layout, myTaskList);
        listView.setAdapter(adapter);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends select data

////////////////////////////////////////////////////////////////////////////////////////////////////  Remove on Long Click

    public void deleteFromDatabase(String titleName){
        //delete from table where name = selectedName;
        SQLiteDatabase db2 =  databaseAdapter.getWritableDatabase();
        ContentValues cv2 = new ContentValues();    // create content values object.
        String[] whereArgs = {titleName};       // string array of the conditions in the array.
        db2.delete(DatabaseAdapter.TABLE_NAME, DatabaseAdapter.TITLE+" =? ", whereArgs);    // query that delete the entry from the database where the condition is satisfied.
        Toast.makeText(this, "Completed Task "+titleName+" Removed", Toast.LENGTH_SHORT).show();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////  ends Remove on Long Click
}
