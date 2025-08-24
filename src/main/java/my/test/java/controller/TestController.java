package my.test.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/string")
    public String getString(){
        return "OK";
    }

    @GetMapping("/client-error/400")
    public ResponseEntity<Void> getError400(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/client-error/404")
    public ResponseEntity<Void> getError404(){
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/server-error/500")
    public ResponseEntity<Void> getError500(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/delay-5-seconds")
    public String getStringAfter5Seconds() throws InterruptedException {
        Thread.sleep(5000);
        return "OK";
    }

    @GetMapping("/delay-10-seconds")
    public String getStringAfter10Seconds() throws InterruptedException {
        Thread.sleep(5000);
        return "OK";
    }


}
