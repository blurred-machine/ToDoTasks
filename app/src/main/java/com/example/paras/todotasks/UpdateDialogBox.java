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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;

/**
 * Created by paras on 5/6/2018.
 */
// a class that entends AppCompatDialogFragment.
public class UpdateDialogBox extends AppCompatDialogFragment {

    // object initialization
    Context context;
    EditText editText1;
    EditText editText2;
    String editTitle;
    String editDescription;
    String editDate;

    // method to set the context of this class as the context passed into it when called.
    public void setContext(Context context){
        this.context = context;
    }

    // method that populates the EditText with the data passed inside the method.
    public void makeEditTextPopulated(String editTitle, String editDescription, String editDate){
        this.editTitle = editTitle;
        this.editDescription = editDescription;
        this.editDate = editDate;
    };

    // a overridden method of the AppCompatDialogFragment class.
    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

        // creating the object of the AlertDialog using Builder.
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        // making the object of the LayoutInflater class.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // inflating the view with the dialog_layout file created before.
        final View view = inflater.inflate(R.layout.dialog_layout, null);

        // setting the view and the visible part inside the Dialog.
        builder2.setView(view);
        builder2.setTitle("Update ToDo");
        builder2.setPositiveButton("Save",null);
        builder2.setNegativeButton("cancel",null);
        final AlertDialog mAlertDialog = builder2.create();
        final DatePicker simpleDatePicker = (DatePicker)view.findViewById(R.id.datePicker1);

        // storing the three parts of the date in different indices of the array of string by spliting the complete string at the "/" sign.
        String str[] = editDate.split("/");
        // storing the respective values in variables.
        final int editDay = Integer.parseInt(str[0]);
        final int editMonth = Integer.parseInt(str[1]);
        final int editYear = Integer.parseInt(str[2]);

        // below mentioned setOnShowListener on the dialog box is similar to the one in DialogBox.java file.
        // for viewing the comments of this section, visit same section in the DilaogBox.java class.
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button pos = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button neg = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                editText1.setText(editTitle);
                editText2.setText(editDescription);
                simpleDatePicker.updateDate(editYear, (editMonth-1), editDay);

                neg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();
                    }
                });

                pos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int day = simpleDatePicker.getDayOfMonth();
                        int month = simpleDatePicker.getMonth();
                        int year = simpleDatePicker.getYear();
                        String title = editText1.getText().toString();
                        String description = editText2.getText().toString();
                        String date = day+"/"+(month+1)+"/"+year;
                        if(title.isEmpty() || description.isEmpty()){
                            Toast.makeText(context, "Empty Field", Toast.LENGTH_SHORT).show();
                        } else {
                            mAlertDialog.dismiss();

                            MainActivity.updateData(title, description, date);
                            Log.i("paras", "UPDATION SUCCESSFULL.. !!");
                            Toast.makeText(context, "Task Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                        }

                    }
                });
            }
        });

        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        return mAlertDialog;
    }
}
