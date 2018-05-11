package com.example.paras.todotasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by paras on 5/5/2018.
 */

// a class DialogBox that extends AppCompatDialogFragment
public class DialogBox extends AppCompatDialogFragment {
    Context context;
    EditText editText1;
    EditText editText2;
    int day;
    int month;
    int year;

    public void setContext(Context context){
        this.context = context;
    }   // method that sets the context to the context of the activity.


    // overridden method of the AppCompatDialogFragment that is triggered for creating the dialog box.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // object of the AlertBox.
        LayoutInflater inflater = getActivity().getLayoutInflater();    // setting the layout inflater to inflate the layout (xml file)
        final View view = inflater.inflate(R.layout.dialog_layout, null);   // inflating the layout for the activity.
        builder.setView(view);  // setting the view for the Alert box.
        builder.setTitle("Create New ToDo");    // title displayed on top of alert box.
        builder.setPositiveButton("Save",null);     // positive button as save the data.
        builder.setNegativeButton("cancel",null);   // negative button as cancel the dialog box.
        final AlertDialog mAlertDialog = builder.create();      // create the above designed alert box.
        // make the object of the date picker by taking the reference to the "id" in the layout file.
        final DatePicker simpleDatePicker = (DatePicker)view.findViewById(R.id.datePicker1);
        // set onClickListener for the dialog box
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // giving the positive and negative buttons as variable names.
                Button pos = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button neg = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                // set onClickListener for the negative button.
                neg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();
                    }
                });
                // set onClickListener for the positive button.
                pos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get the current date in three variables as day, month, year
                        day = simpleDatePicker.getDayOfMonth();
                        month = simpleDatePicker.getMonth();
                        year = simpleDatePicker.getYear();
                        // getting the text entered by the user in the editText.
                        String title = editText1.getText().toString();
                        String description = editText2.getText().toString();
                        String date = day+"/"+(month+1)+"/"+year;
                        // if any of the field is empty then show a message saying empty fields.
                        if(title.isEmpty() || description.isEmpty()){
                            Toast.makeText(context, "Empty Field", Toast.LENGTH_SHORT).show();
                        } else {    // else dismiss the dialog box.
                            mAlertDialog.dismiss();
                            // calling the insertData method from the main activity.
                            MainActivity.insertData(title, description, date);
                            Log.i("paras", "INSERTION SUCCESSFULL.. !!");
                            Toast.makeText(context, "New Task Created Successfully !!", Toast.LENGTH_SHORT).show();
                            // refresh the page by firing an intent on the same activity.
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                        }

                    }
                });
            }
        });
        // initializing the edit text variables using the findViewById of the elements in the layout file.
        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        return mAlertDialog;
    }
}
