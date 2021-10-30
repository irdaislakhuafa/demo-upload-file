package com.demo.uploadfile.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class UploadFIleController {

    private static final String urlUpload = "/home/artix/.cache/uploads/";

    // main page
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // url for upload file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        // debug content type
        System.out.println("Content Type : " + file.getContentType());

        // check file not empty
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/status";
        } else {
            try {
                // save file to bytes
                byte[] bytes = file.getBytes();
                // assign file location to storage
                Path path = Paths.get(urlUpload + file.getOriginalFilename());
                // write file to storage
                Files.write(path, bytes);

                // send attribut to redirect url
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded files with file name \"" + file.getOriginalFilename() + "\"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // redirect to next page
            return "redirect:/status";
        }
    }

    // status upload url
    @GetMapping("/status")
    public String uploadStatus() {
        return "status";
    }
}
