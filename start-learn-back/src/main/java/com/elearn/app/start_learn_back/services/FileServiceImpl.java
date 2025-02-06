//package com.elearn.app.start_learn_back.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//@Service
//public class FileServiceImpl implements FileService {
//    @Override
//    public String save(MultipartFile file, String outputPath, String fileName) throws IOException {
////        Path path = Paths.get(outputPath);
////        // create output folder if not exists
////        Files.createDirectories(path);
////        // path ko join
////        Path filePath = Paths.get(path.toString(), file.getOriginalFilename());
////
////        System.out.println("the filepath " + filePath);
////        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
////        return path.toString();
//        Path uploadDir = Paths.get("uploads/courses/banners");
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//
//        // Define the target path
//        Path targetPath = uploadDir.resolve(file.getOriginalFilename());
//
//        // Copy the file
//        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//
//        return "File uploaded successfully to: " + targetPath.toAbsolutePath();
//    } catch (IOException e) {
//        e.printStackTrace();
//        return "File upload failed: " + e.getMessage();
//    }
//}
//}
package com.elearn.app.start_learn_back.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String save(MultipartFile file, String outputPath, String fileName) {
        try {
            // Define the target directory
            Path uploadDir = Paths.get(outputPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir); // Create directories if they do not exist
            }
            // Define the target file path
            Path targetPath = uploadDir.resolve(fileName);
            // Copy the file to the target location
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "" +targetPath.toString().replace("\\" , "/") ;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }
}