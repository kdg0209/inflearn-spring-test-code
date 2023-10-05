package com.inflearn.testcode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/sql/user-service-test-data.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;
}