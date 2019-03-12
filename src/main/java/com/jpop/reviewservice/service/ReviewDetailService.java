package com.jpop.reviewservice.service;

import com.jpop.reviewservice.model.Review;

import java.util.List;

public interface ReviewDetailService {

    List<Review> getAllReviews(long productId);

    List<Review> getAllReviews();
}
