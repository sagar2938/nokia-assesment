package com.nokia.filestore.service.common;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorMessages
{

    @Getter
    private static List<String> errorMessages = new ArrayList<>();

    public boolean isErrorsPresent() {
        if(errorMessages.isEmpty()){
            return false;
        }
        return true;
    }

    public void add(String message){
        errorMessages.add(message);
    }

    public void clearMessages(){
        errorMessages.clear();
    }
}
