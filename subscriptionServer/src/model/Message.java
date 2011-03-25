/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author CUNEYT
 */
public class Message {

    private Date date;
    private Timestamp time;
    private String content;
    private int category;
    public static final int NEWS = 1;
    public static final int COMMENT = 2;

    public Message(Date date, Timestamp time, String content, int category) {
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
