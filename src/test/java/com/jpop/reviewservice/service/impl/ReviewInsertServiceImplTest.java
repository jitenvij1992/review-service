package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewInsertRepository;
import com.jpop.reviewservice.exception.InvalidReviewException;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewInsertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ReviewInsertServiceImplTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        ReviewInsertService reviewInsertService() {
            return new ReviewInsertServiceImpl();
        }
    }

    @Autowired
    ReviewInsertService reviewInsertService;

    @MockBean
    ReviewInsertRepository reviewInsertRepository;

    @Test
    public void addReview() {
        Review review = new Review(1, "Test product", 4, 2, new Date());
        when(reviewInsertRepository.save(review)).thenReturn(review);
        assertThat(reviewInsertService.addReview(1, review), is(review));
    }

    @Test(expected = InvalidReviewException.class)
    public void updateEmptyReviewTest() {

        Review review = new Review(1, "Test product", 4, 2, new Date());
        when(reviewInsertRepository.findByIdAndProductId(1, 1)).thenReturn(Optional.empty());
        reviewInsertService.updateReview(1, review);
    }

    @Test
    public void updateReviewTest() {
        Review review = new Review(1, "Test product", 4, 2, new Date());
        when(reviewInsertRepository.findByIdAndProductId(1, 1)).thenReturn(Optional.of(review));
        reviewInsertService.updateReview(1, review);
        verify(reviewInsertRepository, times(1)).findByIdAndProductId(1, 1);
        verify(reviewInsertRepository, times(1)).save(review);
    }
}