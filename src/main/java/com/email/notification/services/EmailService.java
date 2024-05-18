package com.email.notification.services;

import com.email.notification.entities.EmailDetails;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails);

}
