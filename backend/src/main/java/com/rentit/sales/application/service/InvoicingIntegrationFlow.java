package com.rentit.sales.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.mail.Mail;

/**
 * Created by akaiz on 5/3/2016.
 */
@Configuration
@IntegrationComponentScan
public class InvoicingIntegrationFlow {

    @Value("${gmail.username}")
    String gmailUsername;
    @Value("${gmail.password}")
    String gmailPassword;
    @Bean
    IntegrationFlow sendInvoiceFlow() {


        return IntegrationFlows.from("sendInvoiceChannel")
                .handle(Mail.outboundAdapter("smtp.gmail.com")
                        .port(465)
                        .protocol("smtps")
                        .credentials(gmailUsername, gmailPassword)
                        .javaMailProperties(p -> p.put("mail.debug", "false"))
                )

                .get();
    }
}


