package com.example.woogisfree.global.common.file;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeProfileImage(MultipartFile file, ApplicationUser currentUser);

    void deleteProfileImage(ApplicationUser currentUser);
}
