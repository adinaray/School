package com.example.School.Model;
import javax.persistence.*;

@Entity
@Table(name="personaldetails")
public class PersonalDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;


    @Column(name="first_Name",nullable = false)
    private String first_Name;

    @Column(name="last_Name",nullable = false)
    private String last_Name;

    @Column(name="phonenumber",nullable = false)
    private long phonenumber;

    public PersonalDetails(int id, String first_Name, String last_Name, long phonenumber) {
        this.id = id;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public PersonalDetails() {
    }

    @Override
    public String toString() {
        return "PersonalDetails{" +
                "id=" + id +
                ", first_Name='" + first_Name + '\'' +
                ", last_Name='" + last_Name + '\'' +
                ", phonenumber=" + phonenumber +
                '}';
    }
}
