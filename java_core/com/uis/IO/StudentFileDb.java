package com.uis.IO;

import java.text.DateFormat;
import java.util.Date;

public class StudentFileDb {
    
}




class Student {
    public final String name;
    public final Date dob;
    int std;
    int rank;

    public Student(String name, String date, int std, int rank){
        this.name = name;
        this.std = std;
        this.rank = rank;
        // this.date = new Date(date);

        // this.date = DateFormat.parse(date);

        this.date = DateFormat.parse(date);

        // try {
        //     dateArr = date.split("-");
        //     date = 
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }

    }

    
}


class DateFormatException {

    
}