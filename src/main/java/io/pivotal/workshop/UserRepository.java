package io.pivotal.workshop;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.pivotal.workshop.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findByCode(String code);
	
	public List<User> findAllByCode(String code);

}
