package com.example.navigationapp_backend.repository;


import com.example.navigationapp_backend.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
@RepositoryQualifier(RepositoryQualifier.RepositoryQualifierType.USERREPOSITORY)
public class UserRepository implements ICrudRepository<User> {
	
	@Inject
	private EntityManager em;

	@Override
	public User save(User t) {
		// TODO Auto-generated method stub
		if(t.getAisId()==null) {
			em.persist(t);
		}else{
			em.merge(t);
		}
		return t;
	}

	@Override
	public void delete(User t) {
		// TODO Auto-generated method stub
		em.remove(t);
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return em.find(User.class, id);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("findAllUsers", User.class).getResultList();
	}


}
