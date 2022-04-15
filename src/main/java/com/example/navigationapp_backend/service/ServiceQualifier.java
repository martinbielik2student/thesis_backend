package com.example.navigationapp_backend.service;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface ServiceQualifier {

	ServiceQualifierType value();
	
	enum ServiceQualifierType{
		USERDTOREPOSITORY
	}
}
