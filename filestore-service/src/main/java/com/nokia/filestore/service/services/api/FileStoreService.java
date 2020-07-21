package com.nokia.filestore.service.services.api;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileStoreService
{
    public String storeFile(MultipartFile file);
    public Resource loadFile(String fileName);
    public List listFiles()
        throws IOException, URISyntaxException;
}
