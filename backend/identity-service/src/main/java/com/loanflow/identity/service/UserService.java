package com.loanflow.identity.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.loanflow.identity.dto.UserProfileDto;

@Service
public class UserService {

    public UserProfileDto getCurrentUserProfile() {
        // TODO: Sprint 3 - Extract user ID from security context and fetch from database
        throw new ResponseStatusException(
            HttpStatus.NOT_IMPLEMENTED, 
            "User profile retrieval is slated for Sprint 3"
        );
    }
}