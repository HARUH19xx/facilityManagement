package com.example.facilitymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 特定の例外に対するハンドラー
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // ログ出力など、エラー処理をここに記述
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // その他の例外に対する汎用的なハンドラー
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // ログ出力など、エラー処理をここに記述
        return new ResponseEntity<>("内部サーバーエラーが発生しました。", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
