package com.example.paras.todotasks;

import java.util.Comparator;

/**
 * Created by paras on 5/6/2018.
 */

// MyTask class containing all the setters and getters for
class MyTask {
    public int image,status;
    public String Title, Description, Date;

    // a method that sets the details of the database to the variables defined here
    MyTask(int image, String Title, String Description, String Date, int status){
        this.image = image;
        this.Title = Title;
        this.Description = Description;
        this.Date = Date;
        this.status = status;
    }

    // getters to get the values of the data from database which were set earlier.
    int getImage(){
        return image;
    }

    String getTitle(){
        return Title;
    }

    String getDescription(){
        return Description;
    }

    String getDate(){
        return Date;
    }

}

// a class implementing the Comparator of type MyTask and is used to sort list in order of Date of finish.
class SortByDate implements Comparator<MyTask>{

    @Override
    public int compare(MyTask t1, MyTask t2) {

        // storing the string of date of both the lists in arrays by splitting it at the "/" symbol.
        String[] t1Arr =t1.Date.split("/") ;
        String[] t2Arr =t2.Date.split("/") ;

        // converting the respective date elements into the integer values so that we can compare them, using Integer.parseInt().
         int t1Day = Integer.parseInt(t1Arr[0]);
         int t1Month = Integer.parseInt(t1Arr[1]);
         int t1Year = Integer.parseInt(t1Arr[2]);

         int t2Day = Integer.parseInt(t2Arr[0]);
         int t2Month = Integer.parseInt(t2Arr[1]);
         int t2Year = Integer.parseInt(t2Arr[2]);

        // Sorting algorithm which i implemented on the year, month and day comparision.
        // if the years are not same then sort on year.
        // else if years are same but months are not same then sort on month.
        // else if both years and months are same then whether the day are same or not sort using day only.

        if (t1Year != t2Year)
            return t1Year - t2Year;
        else if (t1Year == t2Year && t1Month != t2Month)
            return t1Month-t2Month;
        return t1Day - t2Day;
    }
}
