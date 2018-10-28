package com.user.repos;

import org.springframework.data.repository.CrudRepository;

import com.user.data.UserData;

public interface UserRepository extends CrudRepository<UserData, Long> {

}
