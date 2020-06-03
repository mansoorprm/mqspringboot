package com.example.mqspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJms
public class MqspringApplication {

	@Autowired
    private JmsTemplate jmsTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(MqspringApplication.class, args);
	}

	@GetMapping("send/{msg}")
	String send(@PathVariable String msg){
	    try{
	        jmsTemplate.convertAndSend("TEST.QUEUE.1",msg);
	        return "OK";
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}
	
	@GetMapping("publish/{msg}")
	String publish(@PathVariable String msg){
	    try{
	    //    jmsTemplate.convertAndSend("DEV.TEST.TOPIC",msg);
	    	jmsTemplate.setPubSubDomain(true);
	        jmsTemplate.convertAndSend("/dev/JavaTopic", msg);
	        
	        return "OK";
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}
	
	@GetMapping("recv")
	String recv(){
	    try{
	        return jmsTemplate.receiveAndConvert("TEST.QUEUE.1").toString();
	    }catch(JmsException ex){
	        ex.printStackTrace();
	        return "FAIL";
	    }
	}
}
