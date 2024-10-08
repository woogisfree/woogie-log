package com.example.woogisfree.global.common.file;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageProperties fileStorageProperties;
    private final TransactionTemplate transactionTemplate;
    private final UserRepository userRepository;

    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties, PlatformTransactionManager transactionManager, UserRepository userRepository) {
        this.fileStorageProperties = fileStorageProperties;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.userRepository = userRepository;
    }

    private static String getOriginalFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (!fileExtension.equalsIgnoreCase(".jpg")
                    && !fileExtension.equalsIgnoreCase(".jpeg")
                    && !fileExtension.equalsIgnoreCase(".png")) {
                throw new InvalidFileException("Invalid file extension");
            }
        }
        return originalFilename;
    }

    @Override
    public String storeProfileImage(MultipartFile file, ApplicationUser currentUser) {
        return transactionTemplate.execute(status -> {
            try {
                if (currentUser.getProfileImage() != null) {
                    Path existingProfileImagePath = Paths.get(fileStorageProperties.getProfileImagePath()).resolve(currentUser.getProfileImage());
                    Files.deleteIfExists(existingProfileImagePath);
                }

                String originalFilename = getOriginalFilename(file);
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String uniqueFilename = timestamp + "_" + UUID.randomUUID() + "_" + originalFilename;
                Path profileImagePath = Paths.get(fileStorageProperties.getProfileImagePath()).resolve(uniqueFilename);

                Files.createDirectories(profileImagePath.getParent());
                file.transferTo(profileImagePath.toFile());

                currentUser.setProfileImage(uniqueFilename);
                userRepository.save(currentUser);
                return uniqueFilename;
            } catch (InvalidFileException | IOException e) {
                log.error("Failed to store file", e);
                status.setRollbackOnly();
                throw new RuntimeException("Failed to store file", e);
            }
        });
    }

    @Override
    public void deleteProfileImage(ApplicationUser currentUser) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    if (currentUser.getProfileImage() != null) {
                        Path profileImagePath = Paths.get(fileStorageProperties.getProfileImagePath()).resolve(currentUser.getProfileImage());
                        Files.deleteIfExists(profileImagePath);
                        currentUser.setProfileImage(null);
                        userRepository.save(currentUser);
                    }
                } catch (IOException e) {
                    log.error("Failed to delete file", e);
                    status.setRollbackOnly();
                    throw new RuntimeException("Failed to delete file", e);
                }
            }
        });
    }
}
