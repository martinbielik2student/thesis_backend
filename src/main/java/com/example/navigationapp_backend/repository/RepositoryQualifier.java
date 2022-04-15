package com.example.navigationapp_backend.repository;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryQualifier {

	RepositoryQualifierType value();
	
	enum RepositoryQualifierType{
		USERREPOSITORY
	}
}
