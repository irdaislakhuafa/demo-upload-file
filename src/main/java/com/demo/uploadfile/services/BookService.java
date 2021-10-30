package com.demo.uploadfile.services;

import java.util.List;

import com.demo.uploadfile.models.dao.BookDao;
import com.demo.uploadfile.models.entity.Book;
import com.demo.uploadfile.utils.CSVHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    // get all books
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    // save all books
    public List<Book> saveCsvFile(MultipartFile file) {
        try {
            return bookDao.saveAll(
                    // convert CSV file to books
                    CSVHelper.csvToBook(file));

        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("Failed to save CSV Data : " + e.getMessage());
        }
    }

}
