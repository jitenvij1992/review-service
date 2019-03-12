package com.jpop.reviewservice.integration;

import com.jpop.reviewservice.ReviewServiceApplication;
import com.jpop.reviewservice.configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReviewServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public class ApplicationIT {

    @Test
    public void getReviews() {

    }

}
