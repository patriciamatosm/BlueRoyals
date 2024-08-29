package es.uah.client.client.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir").replace("\\", "/") + "/client/client/uploads/";
//private static final String UPLOAD_DIR = "uploads/";


    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println("upload_dir= " + UPLOAD_DIR + filename);
        try {
            File file = new File(UPLOAD_DIR + filename);
            if (file.exists()) {
                Resource resource = new FileSystemResource(file);
                System.out.println("Existe file");

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                System.out.println("No existe");

                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

