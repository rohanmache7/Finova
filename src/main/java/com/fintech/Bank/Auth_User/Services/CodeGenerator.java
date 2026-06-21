package com.fintech.Bank.Auth_User.Services;

import com.fintech.Bank.Auth_User.Repo.PasswordResetCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class CodeGenerator {
    private final PasswordResetCodeRepo passwordResetCodeRepo;

    private static final String ALPHA_NUMERIC  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH  = 5;
    public String generatedUniqueCode() {
        while (true) {
            String code = generateRandomCode();

            if (passwordResetCodeRepo.findByCode(code).isEmpty()) {
                return code;
            }
        }
    }

    private String generateRandomCode(){
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        SecureRandom random = new SecureRandom();

        for(int i=0;i<CODE_LENGTH;i++){
            int idx = random.nextInt(ALPHA_NUMERIC.length());
            sb.append(ALPHA_NUMERIC.charAt(idx));
        }
        return sb.toString();
    }
}
