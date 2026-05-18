package com.bakery.model;

import javax.persistence.*;

@Entity
@Table(name = "contact_messages")
public class ContactMessage extends BaseEntity {

    private String name;
    private String email;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String message;
    private boolean readStatus = false;

    public ContactMessage() {}

    public ContactMessage(String name, String email, String subject, String message) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    // --- Getters & Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isReadStatus() { return readStatus; }
    public boolean getReadStatus() { return readStatus; }
    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }
}
