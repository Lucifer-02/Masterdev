package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//@Document(collection = "books")
@Document(collection = "hoangnlv")
public class Book {

    @Id
    private ObjectId _id;
    private String title;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date public_date;

    private String description;

    public Book(ObjectId _id, String title, String author, Date public_date, String description) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.public_date = public_date;
        this.description = description;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublic_date() {
        return public_date;
    }

    public void setPublic_date(Date public_date) {
        this.public_date = public_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
