package com.ia.desafioia.controllers;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.models.User;
import com.ia.desafioia.services.interfaces.IUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    protected IUserDataService dataService;

    @Autowired
    public UserController(IUserDataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(this.dataService.create(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(this.dataService.findAll());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id){
        return ResponseEntity.ok(this.dataService.findById(id));
    }

    @GetMapping("/user/pagination")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam int page, @RequestParam int limit){
        return ResponseEntity.ok(this.dataService.findAll(page, limit));
    }
}