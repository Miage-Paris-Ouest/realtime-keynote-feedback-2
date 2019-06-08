package miage.nanterre.m1app.realtimekeynote.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadService {
    public static final String uploadDir = "C:\\data-video\\";

    public static void upload( MultipartFile file) throws IOException {
        File convertFile = new File(uploadDir + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }
}
