package com.example.webapp.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class ClientDto {
    @NotEmpty(message = "The First Name is Required")
    private String firstName;

    @NotEmpty(message = "The Last Name is Required")
    private String lastName;

    @NotEmpty(message = "The Email is Required")
    @Email
    private String email;

    private String phone;
    private String address;

    @NotEmpty(message = "The Status is required")
    private String status; // New, permanent, lead, occasional, inactive


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
