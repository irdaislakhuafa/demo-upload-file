package com.demo.uploadfile.models.dao;

import com.demo.uploadfile.models.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {

}
