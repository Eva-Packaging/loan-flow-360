package com.loanflow.identity.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto ( LocalDateTime timestatam,
              int status, String error, String message, String path) {

              }