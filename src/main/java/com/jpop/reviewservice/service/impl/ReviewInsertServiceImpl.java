package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewInsertRepository;
import com.jpop.reviewservice.exception.InvalidReviewException;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewInsertServiceImpl implements ReviewInsertService {

    @Autowired
    private ReviewInsertRepository reviewInsertRepository;

    @Override
    public Review addReview(long productId, Review review) {
        review.setProductId(productId);
        return reviewInsertRepository.save(review);
    }

    @Override
    public void updateReview(long productId, Review review) {
        reviewInsertRepository.findById(review.getId())
                .ifPresentOrElse(reviewData -> addReview(productId, reviewData), () -> new InvalidReviewException("Review is invalid"));
    }
}
