package com.reddit.clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.reddit.clone.exception.SpringRedditException;
import com.reddit.clone.model.NotificationEmail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
    
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    MailContentBuilder mailContentBuilder;


    @Async
    public void sendMail(NotificationEmail notificationEmail)
    {
        MimeMessagePreparator messagePreparator = mimeMessage -> {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("darshankhairnar72@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        

        try{
            javaMailSender.send(messagePreparator);
            log.info("Activation Mail Send");
        }
        catch(MailException e)
        {
            throw new SpringRedditException("Exception Occured While Sending Mail " + notificationEmail.getRecipient(),e);
        }

    }
}
