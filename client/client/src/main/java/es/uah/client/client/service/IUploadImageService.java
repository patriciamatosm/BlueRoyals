package es.uah.client.client.service;

import java.io.InputStream;

public interface IUploadImageService {
    String saveImage(InputStream inputStream, String fileName);
}
