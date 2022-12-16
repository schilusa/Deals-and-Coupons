package com.dealsandcouponsfinder.profilemanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dealsandcouponsfinder.profilemanagement.model.User1;




public interface UserRepo extends MongoRepository<User1, String> {

	User1 findByUsername(String username);

}
