/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author CUNEYT
 */
public class Message {

    private Date date;
    private Time time;
    private String content;
    private int category;
    public static final int SIGNALS = 1;
    public static final int COMMENT = 2;

    public Message(Date date, Time time, String content, int category) {
        this.date = date;
        this.time = time;
        this.content = content;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return date.getTime() + ":" + time.getTime() + ":" + category + ":" + content;
    }
}
