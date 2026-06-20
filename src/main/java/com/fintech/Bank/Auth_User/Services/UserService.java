package com.fintech.Bank.Auth_User.Services;

import com.fintech.Bank.Auth_User.dtos.UpdatePasswordRequest;
import com.fintech.Bank.Auth_User.dtos.UserDTO;
import com.fintech.Bank.Auth_User.entity.User;
import com.fintech.Bank.res.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {



        User getCurrentLoggedInUser();

        Response<UserDTO> getMyProfile();

        Response<Page<UserDTO>> getAllUsers(int page, int size);

        Response<?> updatePassword(UpdatePasswordRequest updatePasswordRequest);

        Response<?> uploadProfilePicture(MultipartFile file);

        Response<?> uploadProfilePictureToS3(MultipartFile file);
    }

