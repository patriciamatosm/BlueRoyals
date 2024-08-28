package es.uah.client.client.service;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadImageServiceImpl implements IUploadImageService{
    @Override
    public String saveImage(InputStream inputStream, String fileName) {
        try {
            Path folder = Paths.get("../images/folder");
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            Path filePath = folder.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


            return "/images/" + fileName;
        } catch (IOException e) {
            Notification.show("Error saving image: " + e.getMessage());
        }
        return null;
    }

}
