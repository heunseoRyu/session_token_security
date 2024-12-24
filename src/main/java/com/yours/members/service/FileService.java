package com.yours.members.service;

import com.yours.members.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    public String uploadFile(MultipartFile file) {
        if(!file.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            String savedName = uuid + "_" + file.getOriginalFilename();
            File dest = new File(fileDir,savedName);
            try{
                file.transferTo(dest);
                return savedName;
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public FileResponse getFile(String name){
        FileResponse res = new FileResponse();
        try{
            File file = new File(fileDir,name);
            res.setBytes(FileCopyUtils.copyToByteArray(file));
            res.setContentType(Files.probeContentType(file.toPath()));
        }catch(Exception e){

        }
        return res;
    }
}
