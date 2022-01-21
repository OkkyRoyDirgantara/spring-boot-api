package com.example.belajar.belajarspringdasar.controllers;

import com.example.belajar.belajarspringdasar.dto.ResponseData;
import com.example.belajar.belajarspringdasar.models.entity.User;
import com.example.belajar.belajarspringdasar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseData<User>> createUser(@Valid @RequestBody User user, Errors errors){
        ResponseData<User> responseData = new ResponseData<>();
        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()){
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }else {
            try {
                // Unique Email Check
                if (userService.isExistByEmail(user.getEmail())){
                    responseData.setStatus(false);
                    responseData.setPayload(null);
                    responseData.getMessages().add("Email has been used");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
                }else {
                    responseData.setStatus(true);
                    responseData.setPayload(userService.save(user));
                    responseData.getMessages().add("User has been created");
                    return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
                }
            }catch (Exception e){
                responseData.setStatus(false);
                responseData.setPayload(null);
                responseData.getMessages().add(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
        }
    }

    @GetMapping
    public ResponseEntity getAllUser(){
        try {
            return ResponseEntity.ok(userService.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.findById(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors){
        ResponseData<User> responseData = new ResponseData<>();
        User currentUser = userService.findById(id);
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }else {
            try {
                currentUser.setName(user.getName());
                currentUser.setEmail(user.getEmail());
                currentUser.setPassword(user.getPassword());
                responseData.setStatus(true);
                responseData.getMessages().add("User has been updated");
                responseData.setPayload(userService.save(currentUser));
                return ResponseEntity.status(HttpStatus.OK).body(responseData);
            }catch (Exception e){
                responseData.setStatus(false);
                responseData.setPayload(null);
                responseData.getMessages().add(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
