package com.ensa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "magazines")
public class Magazine extends Document {
    private String publisher;
    private String issueNumber;
    private Date dateIssue;

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getIssueNumber() { return issueNumber; }
    public void setIssueNumber(String issueNumber) { this.issueNumber = issueNumber; }

    public Date getDateIssue() { return dateIssue; }
    public void setDateIssue(Date dateIssue) { this.dateIssue = dateIssue; }
}
