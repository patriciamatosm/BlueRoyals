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

    private static final String UPLOAD_DIR = System.getProperty("user.dir").replace("\\", "/") + "/client/client/uploads/";

    @Override
    public String saveImage(InputStream inputStream, String fileName) {
        String saveDirectory = UPLOAD_DIR + fileName;
        System.out.println("saveDir= " + saveDirectory);
        try {
            Path folder = Paths.get(UPLOAD_DIR);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            Path filePath = folder.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


            return saveDirectory;
        } catch (IOException e) {
            Notification.show("Error saving image: " + e.getMessage());
            return null;
        }

    }

}
