package com.example.navigationapp_backend.ax;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


public class AppProducer {

	@Produces
	@PersistenceContext
	EntityManager em;
	
	@Produces
	public Logger produceLogger(InjectionPoint ip) {
		return Logger.getLogger(ip.getBean().getBeanClass().getName());
		
	}
}
