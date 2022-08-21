package com.reddit.clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    @Autowired
    TemplateEngine templateEngine;

    String build(String message)
    {
        Context context = new Context();

        context.setVariable("message", message);

        return this.templateEngine.process("mailTemplate", context);
    }
    
}
