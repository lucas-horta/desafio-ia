package com.ia.desafioia.controllers;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.dto.UserUpdateDto;
import com.ia.desafioia.models.GitHubProfile;
import com.ia.desafioia.models.User;
import com.ia.desafioia.services.GitHubDataService;
import com.ia.desafioia.services.interfaces.IGitHubDataService;
import com.ia.desafioia.services.interfaces.IUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    protected final IUserDataService dataService;
    protected final IGitHubDataService gitHubDataService;

    @Autowired
    public UserController(IUserDataService dataService, GitHubDataService gitHubDataService) {
        this.dataService = dataService;
        this.gitHubDataService = gitHubDataService;
    }


    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        return new ResponseEntity<>(this.dataService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(this.dataService.findById(id));
    }

    @PatchMapping(value = "/user/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserUpdateDto dto, Authentication authentication) {
        return new ResponseEntity<>(this.dataService.update(id, dto, authentication.getName()), HttpStatus.OK);
    }


    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok(this.dataService.delete(id));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.dataService.findAll());
    }

    @GetMapping("/users/pagination")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam int page, @RequestParam int limit) {
        return ResponseEntity.ok(this.dataService.findAll(page, limit));
    }

    @GetMapping("/user/{id}/github")
    public GitHubProfile github(@PathVariable("id") String id) {
        return gitHubDataService.github(id);
    }

    @PatchMapping("/iddqd/{id}")
    public ResponseEntity<String> iddqd(@PathVariable String id) {
        return new ResponseEntity<>(this.dataService.iddqd(id), HttpStatus.OK);
    }
}