package com.example.paras.todotasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paras on 5/6/2018.
 */
// a class extending ArrayAdapter class of type MyTask
public class MyListAdapter extends ArrayAdapter<MyTask> {

    // object initialization
    private List<MyTask> myTasks;
    private Context context;
    private int resource;

    // constructor of this class is called to set the class variables as the passed on variables.
    MyListAdapter(Context context, int resource, List<MyTask> myTasks) {
        super(context, resource, myTasks);
        this.context = context;
        this.resource = resource;
        this.myTasks = myTasks;
    }

    // overriding a method ArrayAdapter class that returns a View.
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // creating the object of the LayoutInflater class.
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        // inflating the the view with the resource mentioned above.
        View view = layoutInflater.inflate(resource, null, false);
        // initializing the objects of the following views by referencing to their id.
        ImageView imageView = view.findViewById(R.id.imageView101);
        TextView titleViewName = view.findViewById(R.id.Title101);
        TextView descriptionViewTeam = view.findViewById(R.id.Description101);
        TextView dateViewName = view.findViewById(R.id.Date101);
        TextView headingView = view.findViewById(R.id.Heading101);

        // getting the position of the element in the array list.
        MyTask myTask = myTasks.get(position);
        // set the image in the image view.
        imageView.setImageDrawable(context.getResources().getDrawable(myTask.getImage()));
        // setting the details in the respective veiws.
        titleViewName.setText(myTask.getTitle());
        descriptionViewTeam.setText(myTask.getDescription());
        dateViewName.setText(myTask.getDate());
        headingView.setText(myTask.getDate());
        return view;    // this method returns the view that we built till now.
    }
}