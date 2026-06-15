package com.fintech.Bank.Notification.Services;

import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.Notification.Repo.NotificationRepo;
import com.fintech.Bank.Notification.dtos.NotificationDTO;
import com.fintech.Bank.Notification.entity.Notification;
import com.fintech.Bank.enums.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepo notificationRepo;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO, User user){
try {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
    helper.setTo(notificationDTO.getRecipient());
    helper.setSubject(notificationDTO.getSubject());

    if (notificationDTO.getTemplateName() != null) {
        Context context = new Context();
        context.setVariables(notificationDTO.getTemplateVariables());
        String htmlContent = templateEngine.process(notificationDTO.getTemplateName(), context);
        helper.setText(htmlContent, true);


    } else {
        helper.setText(notificationDTO.getBody(), true);
    }

    mailSender.send(mimeMessage);

    //now save to our database table
    Notification notificationToSave = Notification.builder().recipient(notificationDTO.getRecipient()).subject(notificationDTO.getSubject()).body(notificationDTO.getBody()).type(NotificationType.EMAIL).user(user).build();
    notificationRepo.save(notificationToSave);
}
catch(MessagingException e){
       log.error(e.getMessage());
        }
    }
}
