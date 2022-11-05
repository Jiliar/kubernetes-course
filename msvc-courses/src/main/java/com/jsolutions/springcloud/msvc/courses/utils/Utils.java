package com.jsolutions.springcloud.msvc.courses.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "Error en el campo "+err.getField()+" : "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
