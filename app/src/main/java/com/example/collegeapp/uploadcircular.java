package com.example.collegeapp;

public class uploadcircular {

    String head;
    String body;
    String name;
    String date;

    public uploadcircular(String head, String body, String name, String date) {
        this.head = head;
        this.body = body;
        this.name = name;
        this.date = date;
    }

    public String getHead() {
        return head;
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
