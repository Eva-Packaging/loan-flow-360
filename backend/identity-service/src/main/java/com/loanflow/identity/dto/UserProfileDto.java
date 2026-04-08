package com.loanflow.identity.dto;

import java.util.List;

public record UserProfileDto(Long id, String username, String fullName, String role, 
                            List<String> permissions) {
}