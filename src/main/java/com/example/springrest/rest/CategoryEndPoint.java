package com.example.springrest.rest;

import com.example.springrest.model.Category;
import com.example.springrest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CategoryEndPoint {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category/add")
    public ResponseEntity add(@RequestBody Category category){
        if(categoryRepository.findByName(category.getName()) != null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        categoryRepository.save(category);
        return ResponseEntity
                .ok(category);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isPresent()){
            categoryRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();

    }

    @PutMapping("/category/update")
    public ResponseEntity update(@RequestBody Category category){
       if (categoryRepository.findById(category.getId()).isPresent()){
           categoryRepository.save(category);
           return ResponseEntity
                   .ok()
                   .build();
       }
       return ResponseEntity
               .notFound()
               .build();
    }

    @GetMapping("/category/all")
    public ResponseEntity getAll(){
       return ResponseEntity.ok(categoryRepository.findAll());
    }

}
