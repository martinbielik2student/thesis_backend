package com.example.navigationapp_backend.repository;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ICrudRepository<T> {

	T save(T t);
	
	void delete(T t);
	
	T getById(Long id);
	
	List<T> getAll();
}
