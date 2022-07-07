package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private UserService userService;
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<String> getAllFileNames(Authentication authentication){
        return fileMapper.getAllFileNames(userService.getUserId(authentication));
    }

    public File getFile(String filename, Authentication authentication){
        return fileMapper.getFile(filename, userService.getUserId(authentication));
    }

    public int addFile(Authentication authentication, MultipartFile fileUpload) throws IOException {
        return fileMapper.insertFile(new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()), userService.getUserId(authentication), fileUpload.getBytes()));
    }

    public int deleteFile(Authentication authentication, String fileName) {
        return fileMapper.deleteFile(fileName, userService.getUserId(authentication));
    }
}
