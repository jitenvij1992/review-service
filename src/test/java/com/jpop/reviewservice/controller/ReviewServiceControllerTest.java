package com.jpop.reviewservice.controller;

import com.google.gson.Gson;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewDeleteService;
import com.jpop.reviewservice.service.ReviewDetailService;
import com.jpop.reviewservice.service.ReviewInsertService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewServiceController.class)
public class ReviewServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewInsertService reviewInsertService;

    @MockBean
    private ReviewDetailService reviewDetailService;
    @MockBean
    private ReviewDeleteService reviewDeleteService;

    private List<Review> reviews = new ArrayList<>();

    @Before
    public void setUp() {
        reviews = Arrays.asList(new Review(1, "Test product", 4, 1, new Date()),
                new Review(2, "Test product", 1, 1, new Date()));
    }

    @Test
    public void getReviewsTest() throws Exception {
        when(reviewDetailService.getAllReviews(anyLong())).thenReturn(reviews);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/1/reviews").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is(reviews.get(0).getDescription())));
    }

    @Test
    public void deleteReview() throws Exception {
        doNothing().when(reviewDeleteService).deleteReview(anyLong(), anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/1/reviews/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void addReview() throws Exception {
        Review review = new Review(1, "Test product", 4, 2, new Date());
        when(reviewInsertService.addReview(anyLong(), any(Review.class))).thenReturn(review);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/2/reviews")
                .content(new Gson().toJson(review))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}