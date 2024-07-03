package com.a88.exception;

import com.a88.Pojo.result;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public result ex(Exception ex) {
        ex.printStackTrace();
        return result.error("sorry, action is failed, please contact our staff");
    }

    @ExceptionHandler(AsyncRequestNotUsableException.class)
    public ResponseEntity<String> handleAsyncRequestNotUsableException(AsyncRequestNotUsableException e) {
        // 记录异常信息
        e.printStackTrace();
        return ResponseEntity.status(500).body("Async Request Error: " + e.getMessage());
    }

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<String> handleClientAbortException(ClientAbortException e) {
        // 记录异常信息
        e.printStackTrace();
        return ResponseEntity.status(500).body("Client Abort Error: " + e.getMessage());
    }
}