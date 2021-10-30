package com.demo.uploadfile.controllers.restcontrollers;

import java.util.Arrays;

import com.demo.uploadfile.helpers.ResponseMessage;
import com.demo.uploadfile.services.BookService;
import com.demo.uploadfile.utils.CSVHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // get all books
    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new ResponseMessage(true, null, bookService.findAllBooks()));
    }

    // post/upload csv file
    @PostMapping("/upload/csv")
    public ResponseEntity<?> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        ResponseMessage responseMessage = new ResponseMessage();

        // debug file content type
        System.out.println("Content Type : " + file.getContentType());
        System.out.println("Is CSV? : " + CSVHelper.isCSVFile(file));

        try {
            if (CSVHelper.isCSVFile(file)) {
                responseMessage.setStatus(CSVHelper.isCSVFile(file));
                responseMessage.getMessages().add("Succesfully uploaded and save a files");
                responseMessage.setData(bookService.saveCsvFile(file));
                return ResponseEntity.ok(responseMessage);
            } else {
                responseMessage.setStatus(false);
                responseMessage.getMessages().add("File is not CSV file, please upload CSV file!");
                responseMessage.setData(null);
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException re) {
            responseMessage.setStatus(false);
            responseMessage.getMessages().clear();
            responseMessage.getMessages().add("CSV File contains invalid Headers!");
            responseMessage.setData(null);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Message : " + e.getMessage());
            throw new RuntimeException("Unknown errors, failed to upload and save a CSV file!");
        }

    }
}
