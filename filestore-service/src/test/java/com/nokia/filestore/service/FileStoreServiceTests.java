package com.nokia.filestore.service;

import com.nokia.filestore.service.services.api.FileStoreService;
import org.junit.Test;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStoreServiceTests
{


    @Autowired
    FileStoreService fileStoreService;

    @Test
    public void listFilesTest()
        throws IOException, URISyntaxException
    {

        List files = fileStoreService.listFiles();
        Assert.assertNotNull(files);
        Assert.assertTrue(files.size()>0);
    }

    @Test
    public void loadFileTest()
    {

        Resource resource = fileStoreService.loadFile("test2.java.zip");
        Assert.assertNotNull(resource);

    }
    @Test
    public void storeFileTest(){
        MultipartFile file = new MockMultipartFile("test888.zip", new byte[]{});
        String fileName = fileStoreService.storeFile(file);
        Assert.assertNotNull(fileName);
        Assert.assertEquals(fileName,"test888.zip");
    }

}
