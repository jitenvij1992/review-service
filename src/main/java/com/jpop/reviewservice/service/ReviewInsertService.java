package com.jpop.reviewservice.service;

import com.jpop.reviewservice.model.Review;

public interface ReviewInsertService {

    Review addReview(long productId, Review review);
}
