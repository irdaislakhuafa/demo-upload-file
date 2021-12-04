package com.demo.uploadfile.helpers;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private Object data;
}
