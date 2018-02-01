package edu.mum.mumsched.repository;


import edu.mum.mumsched.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends BaseUserRepository<User> {
}
