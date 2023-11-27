package com.electrica.Electrica.Entity;

import java.util.Date;

public class Message {

    private String from;
    private String text;
    private Date date; // or use LocalDateTime or any other date-time representation

    // Standard getters and setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Constructor for convenience
    public Message(String from, String text, Date date) {
        this.from = from;
        this.text = text;
        this.date = date;
    }

    // Default constructor for serialization/deserialization
    public Message() {
    }

    // toString, hashCode, equals methods as needed
}
