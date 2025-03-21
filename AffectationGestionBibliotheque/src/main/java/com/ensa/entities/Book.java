package com.ensa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "books")
public class Book extends Document {
    private String author;
    private String isbn;
    private Date datePublication;

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }
}
