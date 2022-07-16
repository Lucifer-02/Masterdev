package com.example.demo.Controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Models.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class ApiControllers {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/home")
    public String getPage(){
        return "Hoang dz";
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody  User user){
        userService.saveUser(user);
        return "Saved";
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("there is not this id: " + id));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails){
        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("there is not this id: " + id));
        userService.updateUser(user, userDetails);
        return ResponseEntity.ok(user);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id){
        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("there is not this id: " + id));

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
