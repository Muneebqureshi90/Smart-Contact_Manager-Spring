package com.smartcontactmanger.smartcontactmangerproject.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class ImageUploadService {

    public static String save(MultipartFile file, String userCase) throws IOException {
        String docBase;
        String filePath;

        String imageName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        // Assuming you have a base directory where you want to store the images
        // and 'userCase' represents a subdirectory for different user cases.
        docBase = "your-base-directory/" + userCase;
        filePath = docBase + "/" + imageName;

        // Create the directory if it doesn't exist
        File directory = new File(docBase);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the file to the specified path
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            IOUtils.copy(file.getInputStream(), outputStream);
        }

        return imageName;
    }
}
