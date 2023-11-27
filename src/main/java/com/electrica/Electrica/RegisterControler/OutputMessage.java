package com.electrica.Electrica.RegisterControler;

import com.electrica.Electrica.Entity.Message;

import java.util.Date;

public class OutputMessage extends Message {
    public OutputMessage(String from, String text, Date date) {
        super(from, text, date);
    }
}
