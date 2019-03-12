package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewDetailRepository;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ReviewDetailServiceImplTest {

    @TestConfiguration
    static class ReviewDetailServiceConfig {

        @Bean
        ReviewDetailService reviewDetailService() {
            return new ReviewDetailServiceImpl();
        }
    }

    @Autowired
    ReviewDetailService reviewDetailService;

    @MockBean
    ReviewDetailRepository reviewDetailRepository;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @Test
    public void getAllReviews() {
        List<Review> reviews = Arrays.asList(new Review(1, "Test product", 4, 1, new Date()),
                new Review(2, "Test product", 1, 1, new Date()));
        when(reviewDetailRepository.findAllByProductId(1)).thenReturn(reviews);
        assertThat(reviewDetailService.getAllReviews(1), hasSize(2));
        verify(reviewDetailRepository, times(1)).findAllByProductId(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(1L));
    }
}