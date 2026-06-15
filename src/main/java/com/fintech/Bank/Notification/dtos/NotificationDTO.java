package com.fintech.Bank.Notification.dtos;

import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@Builder
@Table(name = "Notification")
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    @Id
    private Long id;

    private String subject;

    @NotBlank(message = "Recipient is required")
    private String recipient;
    public String getRecipient() {
        return recipient;
    }

    private String body;


    private NotificationType type;


    private  LocalDateTime createdAt;
    //for values/variables to be passed into email template to send
    private String templateName;
    @Transient
    private Map<String,Object> templateVariables;

}
