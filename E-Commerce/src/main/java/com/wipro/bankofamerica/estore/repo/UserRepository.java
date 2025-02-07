package com.wipro.bankofamerica.estore.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.bankofamerica.estore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByCity(String city);
	
	User findByUsername(String username);

}
