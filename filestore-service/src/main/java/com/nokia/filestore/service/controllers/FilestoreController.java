package com.nokia.filestore.service.controllers;


import com.nokia.filestore.service.services.api.FileStoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/filestore")
public class FilestoreController
{


    @Autowired
    FileStoreService fileStoreService;
    @Value("${file.path}")
    private String storePath;
    @RequestMapping(value = "/listFiles", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ApiOperation(value = "list all the files",
        notes = "This API will list all the files",
        response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Request contained data that was invalid"),
        @ApiResponse(code = 200, message = "files list is  returned"),
        @ApiResponse(code = 500, message = "Any server exceptions") })
    public List listFiles()
        throws IOException, URISyntaxException
    {

        List files = new ArrayList();

        files = fileStoreService.listFiles();

        return files;
    }
    @PostMapping("/uploadFile")
    @ApiOperation(value = "upload a file",
        notes = "This API will upload a file",
        response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Request contained data that was invalid"),
        @ApiResponse(code = 200, message = "File is successfully uploaded"),
        @ApiResponse(code = 500, message = "Any server exceptions") })
    public String uploadFile(@RequestParam("file") MultipartFile file){

        String fileName = fileStoreService.storeFile(file);
        return fileName;
    }

    @GetMapping("/downloadFile/{fileName}")
    @ApiOperation(value = "download the file",
        notes = "This API will download the file",
        response = ResponseEntity.class)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Request contained data that was invalid"),
        @ApiResponse(code = 200, message = "returns the file"),
        @ApiResponse(code = 500, message = "Any server exceptions") })
    public ResponseEntity<Resource> pullFile(@PathVariable String fileName){

        Resource resource = fileStoreService.loadFile(fileName);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }




}
