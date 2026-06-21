package com.fintech.Bank;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class BankApplication {
//private final NotificationService notificationService;
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}


//	@Bean
//	CommandLineRunner runner(){
//		return args -> {
//			NotificationDTO notificationDTO = NotificationDTO.builder().recipient("rohanmache1976@gmail.com").subject("Hello testing email").body("Hey,Nigga WTF").type(NotificationType.EMAIL).build();
//			notificationService.sendEmail(notificationDTO,new User());
//		};
//	}
}


