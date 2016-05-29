package com.rentit.sales.application.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import javax.mail.internet.MimeMessage;

/**
 * Created by akaiz on 5/3/2016.
 */
@MessagingGateway
public interface InvoiceGateway {
    @Gateway(requestChannel = "sendInvoiceChannel")
 public void  sendInvoice(MimeMessage msg)  ;




}



