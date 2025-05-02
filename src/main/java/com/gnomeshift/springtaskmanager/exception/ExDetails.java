package com.gnomeshift.springtaskmanager.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ExDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ExDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
