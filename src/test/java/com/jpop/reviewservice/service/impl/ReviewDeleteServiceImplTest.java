package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewDeleteRepository;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewDeleteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ReviewDeleteServiceImplTest {

    @Autowired
    ReviewDeleteService reviewDeleteService;
    @MockBean
    ReviewDeleteRepository reviewDeleteRepository;
    @Captor
    ArgumentCaptor<Long> acLong;

    @Test
    public void deleteReview() {
        Review review = new Review(1, "Test product", 4, 2, new Date());
        when(reviewDeleteRepository.findByIdAndProductId(anyLong(), anyLong())).thenReturn(java.util.Optional.of(review));
        doNothing().when(reviewDeleteRepository).deleteByIdAndProductId(anyLong(), anyLong());
        reviewDeleteService.deleteReview(1, 1);
        verify(reviewDeleteRepository, times(1)).deleteByIdAndProductId(acLong.capture(), acLong.capture());
        List allValues = acLong.getAllValues();
        assertEquals(2, allValues.size());
    }

    @TestConfiguration
    static class ReviewDeleteServiceConfig {
        @Bean
        ReviewDeleteService reviewDeleteService() {
            return new ReviewDeleteServiceImpl();
        }
    }
}