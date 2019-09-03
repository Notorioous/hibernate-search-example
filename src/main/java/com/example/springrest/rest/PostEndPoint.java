package com.example.springrest.rest;

import com.example.springrest.model.Post;
import com.example.springrest.repository.PostRepository;
import com.example.springrest.searchService.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PostEndPoint {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserSearch userSearch;

    @GetMapping("/post/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/findPostByUserName/{name}")
    public ResponseEntity getPostsByUserName(@PathVariable("name") String name){
      return ResponseEntity.ok(userSearch.findPostByUserName(name));
    }

    @PostMapping("/post/add")
    public ResponseEntity add(@RequestBody Post post){
        Optional<Post> byId = postRepository.findById(post.getId());
        if(!byId.isPresent()){
            postRepository.save(post);
            return ResponseEntity.ok(post);
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            postRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PutMapping("/post/update")
    public ResponseEntity update(@RequestBody Post post){
        Optional<Post> byId = postRepository.findById(post.getId());
        if(byId.isPresent()){
            postRepository.save(post);
            return ResponseEntity
                    .ok(post);
        }
        return ResponseEntity
                .notFound()
                .build();
    }
}
