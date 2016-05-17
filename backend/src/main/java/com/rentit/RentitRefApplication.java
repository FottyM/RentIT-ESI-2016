package com.rentit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@EntityScan(basePackageClasses = { RentitRefApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
//@Component
//@IntegrationComponentScan
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RentitRefApplication {

	@Configuration
	static class ObjectMapperCustomizer {
		@Autowired @Qualifier("_halObjectMapper")
		private ObjectMapper springHateoasObjectMapper;

		@Bean(name = "objectMapper")
		ObjectMapper objectMapper() {
			return springHateoasObjectMapper
					.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
					.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
					.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
					.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
					.registerModules(new JavaTimeModule());
		}
		@Bean
		public RestTemplate restTemplate() {
			RestTemplate _restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			messageConverters.add(new MappingJackson2HttpMessageConverter(springHateoasObjectMapper));
			_restTemplate.setMessageConverters(messageConverters);
			return _restTemplate;
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(RentitRefApplication.class, args);

	}
//
//	@Value("${gmail.username}")
//	String gmailUsername;
//	@Value("${gmail.password}")
//	String gmailPassword;
//	@Bean
//	public IntegrationFlow receiveRemittanceAdviceFlow(){
//		return IntegrationFlows
//				.from(Mail.imapIdleAdapter(String.format("imaps://%s:%s@imap.gmail.com/INBOX", gmailUsername, gmailPassword))
//						.selectorExpression("subject matches '.*Remittance .*'")
//				)
//				.transform("@remittanceAdviceProcessor.extractInvoice(payload)")
//				.handle("remittanceAdviceProcessor", "processRemittance")
//				.get();
//	}
//

}