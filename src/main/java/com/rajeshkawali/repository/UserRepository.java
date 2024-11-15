package com.rajeshkawali.repository;

import com.rajeshkawali.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rajesh_Kawali
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
