package com.example.android.taskplanner;

import com.example.android.taskplanner.Model.taskModel;

import java.util.Calendar;
import java.util.Comparator;

public class callsComparator implements Comparator<taskModel> {
    @Override
    public int compare(taskModel t1, taskModel t2) {
            String date1 = t1.getDate();
            String date2 = t2.getDate();
            String cyear,cmonth,cday;
            cyear = "";
            cmonth = "";
            cday = "";
            int check = 0;
            for(int i = 0;i<date1.length();i++){
                if(date1.charAt(i) == '/')
                {
                    check+= 1;
                    continue;
                }
                if(check == 0){
                    cday += date1.charAt(i);
                }
                if(check == 1){
                    cmonth += date1.charAt(i);
                }
                if(check == 2){
                    cyear += date1.charAt(i);
                }
            }
            int day = Integer.parseInt(cday);
            int month = Integer.parseInt(cmonth);
            int year = Integer.parseInt(cyear);
            month-=1;
            Calendar first = Calendar.getInstance();
            first.set(year,month,day,t1.getHour(),t1.getMinute(),0);
            cyear = "";
            cmonth = "";
            cday = "";
            check = 0;
            for(int i = 0;i<date2.length();i++){
                if(date2.charAt(i) == '/')
                {
                    check+= 1;
                    continue;
                }
                if(check == 0){
                    cday += date2.charAt(i);
                }
                if(check == 1){
                    cmonth += date2.charAt(i);
                }
                if(check == 2){
                    cyear += date2.charAt(i);
                }
            }
            day = Integer.parseInt(cday);
            month = Integer.parseInt(cmonth);
            year = Integer.parseInt(cyear);
            month -= 1;
            Calendar second = Calendar.getInstance();
            second.set(year,month,day,t2.getHour(),t2.getMinute(),0);
            if(first.getTimeInMillis()<second.getTimeInMillis()){
                return -1;
            }
            else if(first.getTimeInMillis()>second.getTimeInMillis()){
                return 1;
            }
            return -1;
    }
}
