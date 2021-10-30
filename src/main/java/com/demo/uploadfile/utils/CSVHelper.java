package com.demo.uploadfile.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.demo.uploadfile.models.entity.Book;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {

    // check is csv file
    public static boolean isCSVFile(MultipartFile file) {
        return file.getContentType().equalsIgnoreCase("text/csv");
    }

    public static List<Book> csvToBook(MultipartFile file) {
        List<Book> result = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    // get input stream/data from file
                    new InputStreamReader(
                            // get input stream
                            file.getInputStream(),
                            // encoding
                            "UTF-8"));

            // parse csv file with commons-csv dependency
            CSVParser csvParser = new CSVParser(
                    // reader
                    bufferedReader,
                    // format
                    CSVFormat.DEFAULT.
                    // first record as headers
                            withFirstRecordAsHeader().
                            // ignore case headers
                            withIgnoreHeaderCase().withTrim());

            // add csvRecords to Books
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                result.add(new Book(
                        // id
                        Long.parseLong(csvRecord.get("Id")),
                        // Title
                        csvRecord.get("Title"),
                        // description
                        csvRecord.get("Description"),
                        // price
                        Double.parseDouble(csvRecord.get("Price"))));
            }
            csvParser.close();
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("Failed to convert CSV to Book : " + e.getMessage());
        }
        return result;
    }
}
