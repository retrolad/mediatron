package com.retrolad.mediatron.service;

import com.retrolad.mediatron.dto.UserProfileDto;
import com.retrolad.mediatron.mapper.UserProfileMapper;
import com.retrolad.mediatron.model.user.User;
import com.retrolad.mediatron.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileMapper userMapper;

    public UserProfileDto getUserProfile(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }
}
