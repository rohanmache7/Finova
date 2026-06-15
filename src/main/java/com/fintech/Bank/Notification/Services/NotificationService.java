package com.fintech.Bank.Notification.Services;

import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.Notification.dtos.NotificationDTO;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO,User user);
}
