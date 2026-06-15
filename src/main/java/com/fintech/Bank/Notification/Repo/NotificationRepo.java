package com.fintech.Bank.Notification.Repo;

import com.fintech.Bank.Notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification,Long> {

}
