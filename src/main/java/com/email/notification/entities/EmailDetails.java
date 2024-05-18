package com.email.notification.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDetails {

    private String recipient;
    private String messageBody;
    private String subject;


}
