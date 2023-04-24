package com.example.realpg;

import java.util.Comparator;

public class ActivityComparator implements Comparator<Activity> {
    @Override
    public int compare(Activity a1, Activity a2) {
        if(a1.getTotalMinutes() < a2.getTotalMinutes()) return 1;
        else return -1;
    }
}
