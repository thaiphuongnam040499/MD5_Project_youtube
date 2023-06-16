package rikkei.academy.service.uploadFile;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceIMPL implements FileStorageService{
    private final Path PATH_ROOT = Paths.get("uploadFile");
    @Override
    public void init() {
        try {
            Files.createDirectories(PATH_ROOT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Path uploadFile(MultipartFile multipartFile) {
        try {
            Files.copy(multipartFile.getInputStream(), PATH_ROOT.resolve(multipartFile.getOriginalFilename()));
            return PATH_ROOT.resolve(multipartFile.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       
    }

    @Override
    public Resource load(String filename) {
        return null;
    }
}
