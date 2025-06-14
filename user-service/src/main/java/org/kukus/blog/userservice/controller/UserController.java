package org.kukus.blog.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kukus.blog.userservice.dto.UserCreateDTO;
import org.kukus.blog.userservice.dto.UserDTO;
import org.kukus.blog.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping("/batch/ids")
    public ResponseEntity<Map<String, UserDTO>> getUser(@RequestBody Set<String> ids) {
        List<UserDTO> users = userService.getUsersByIds(ids);

        Map<String, UserDTO> userMap = users.stream()
                .collect(Collectors.toMap(UserDTO::getId, Function.identity()));

        return ResponseEntity.ok(userMap);
    }

//    // Create a new user
//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
//        UserDTO userDTO = userService.createUser(userCreateDTO);
//        return ResponseEntity.ok(userDTO);
//    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        UserDTO userDTO = userService.findUserById(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.findUserByEmail(email);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    // Получение информации о текущем пользователе
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(HttpServletRequest request) {
        String userName = (String) request.getAttribute("userName");
        return ResponseEntity.ok("User Name: " + userName);
    }

    // Удаление пользователя (только для админов)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, HttpServletRequest request) {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can delete users");
        }
        // Здесь может быть логика удаления пользователя
        return ResponseEntity.ok("User " + id + " deleted");
    }
}