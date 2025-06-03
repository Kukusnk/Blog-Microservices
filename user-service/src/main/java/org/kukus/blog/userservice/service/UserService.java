package org.kukus.blog.userservice.service;

import org.kukus.blog.userservice.dto.UserCreateDTO;
import org.kukus.blog.userservice.dto.UserDTO;
import org.kukus.blog.userservice.models.User;
import org.kukus.blog.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Service layer for handling user-related business logic
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void mapUserToUserDTO(List<User> users, List<UserDTO> userDTOs) {
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setFollowers(user.getFollowers());
            userDTOs.add(userDTO);
        }
    }

    public List<UserDTO> getUsersByIds(Set<String> ids) {
        List<User> users = userRepository.findAllByIdIn(ids);
        List<UserDTO> userDTOs = new ArrayList<>();
        mapUserToUserDTO(users, userDTOs);
        return userDTOs;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        mapUserToUserDTO(users, userDTOs);
        return userDTOs;
    }

    // Create a new user from UserCreateDTO
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword()); // In a real app, hash the password before saving
        user.setCreatedAt(LocalDateTime.now());


        User savedUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setCreatedAt(savedUser.getCreatedAt());
        userDTO.setFollowers(savedUser.getFollowers());

        return userDTO;
    }

    // Find user by ID and return as UserDTO
    public UserDTO findUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return getUserDTO(user);
    }

    // Find user by email and return as UserDTO
    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return getUserDTO(user);
    }

    private UserDTO getUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());

        return userDTO;
    }


}
