package com.example.springrest.rest;

import com.example.springrest.model.User;
import com.example.springrest.repository.UserRepository;
import com.example.springrest.searchService.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserEndPoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSearch userSearch;

    @PostMapping("/user/add")
    public ResponseEntity add(@RequestBody User user){

        if(userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

//    @GetMapping("/user")
//    public ResponseEntity getAll(@RequestParam(value = "name", required = false) String name){
//        return ResponseEntity.ok(userSearch.searchUserWithKeyword(name));
//    }

    @GetMapping("/findUserByName/{name}")
    public ResponseEntity getUserByName(@PathVariable("name") String name){
        List<User> users = userSearch.searchUserWithKeyword(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findUserByNameWithFuzzy/{name}")
    public ResponseEntity getUserByNameWithFuzzy(@PathVariable("name") String name){
        List<User> users = userSearch.searchUserNameByFuzzyQuery(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findUserByNameBySort/{name}")
    public ResponseEntity getUserByNameBySort(@PathVariable("name") String name){
        List<User> users = userSearch.searchUserNameByFuzzyQueryBySort(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getById(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
          return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PutMapping("/user/update")
    public ResponseEntity update(@RequestBody User user){
       if(userRepository.findById(user.getId()).isPresent()){
           userRepository.save(user);
           return ResponseEntity
                   .ok()
                   .build();
       }
       return ResponseEntity
               .notFound()
               .build();
    }

}
