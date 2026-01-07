package com.ensta.myfilmlist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/data_test.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class MyfilmlistApplicationTests {

	@Test
	void contextLoads() {
	}

}
