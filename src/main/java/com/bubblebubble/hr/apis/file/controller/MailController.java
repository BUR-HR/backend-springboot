package com.bubblebubble.hr.apis.file.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class MailController {
    
}
