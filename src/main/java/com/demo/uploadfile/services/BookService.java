package com.demo.uploadfile.services;

import java.util.List;

import com.demo.uploadfile.models.dao.BookDao;
import com.demo.uploadfile.models.entity.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

}
