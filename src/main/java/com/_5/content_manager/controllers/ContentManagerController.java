package com._5.content_manager.controllers;
import com._5.content_manager.entities.*;
import com._5.content_manager.services.DataGeneratorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ContentManagerController {

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/insert-many-users")
    public ResponseEntity<List<User>> saveManyUsers(@RequestBody List<User> users){
        return ResponseEntity.ok(dataGeneratorService.generateUsers(users));
    }

    @GetMapping("/fake-comments")
    public ResponseEntity<List<Comment>> getFakeComments(){
        return ResponseEntity.ok(dataGeneratorService.generateComments());
    }

    @GetMapping("/fake-posts")
    public ResponseEntity<List<Post>> getFakePosts(){
        return ResponseEntity.ok(dataGeneratorService.generatePosts());
    }


}
