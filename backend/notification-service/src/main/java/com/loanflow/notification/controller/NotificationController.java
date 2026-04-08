package com.loanflow.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificationService")
public class NotificationController {

    @GetMapping("/status")
    public String getStatus() {
        return "Notification Service is up and running!";
    }
}