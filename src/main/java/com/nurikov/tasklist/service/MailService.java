package com.nurikov.tasklist.service;

import com.nurikov.tasklist.domain.MailType;
import com.nurikov.tasklist.domain.user.User;

import java.util.Properties;

public interface MailService {
    void sendEmail(User user, MailType type, Properties params);
}
