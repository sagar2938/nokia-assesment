package com.nokia.filestore.service.services.impl;

import com.nokia.filestore.service.services.api.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStoreServiceImpl implements FileStoreService
{
    private final Path fileStorageLocation;

    private String storePath;
    @Autowired
    public FileStoreServiceImpl(ConfigurableEnvironment environment)
        throws URISyntaxException, IOException
    {
        this.storePath = environment.getProperty("file.path");
        URI uri = getClass().getResource(storePath).toURI();
        if (uri.getScheme().equals("jar")) {

            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            fileStorageLocation = fileSystem.getPath("/BOOT-INF/classes" + storePath);
        } else {

            fileStorageLocation = Paths.get(uri);
        }

    }

    @Override
    public String storeFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getName());

        try {

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation );


        } catch (IOException ex) {
            System.out.println(ex);
        }
        return fileName;
    }

    @Override
    public Resource loadFile(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("resource doesnot exist");
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return null;
    }

    @Override
    public List listFiles()
        throws IOException, URISyntaxException
    {

        if(org.apache.commons.lang.StringUtils.isNotEmpty(storePath)){
            List files =  Files.walk(fileStorageLocation).map(path -> path.getFileName()).collect(
                Collectors.toList());
            return files;
        }
        return new ArrayList();
    }
}
