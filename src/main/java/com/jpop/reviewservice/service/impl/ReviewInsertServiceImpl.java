package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewInsertRepository;
import com.jpop.reviewservice.exception.InvalidReviewException;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewInsertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewInsertServiceImpl implements ReviewInsertService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewInsertServiceImpl.class);

    @Autowired
    private ReviewInsertRepository reviewInsertRepository;

    @Override
    public Review addReview(long productId, Review review) {
        logger.info("Updating database with the request");
        review.setProductId(productId);
        return reviewInsertRepository.save(review);
    }

    @Override
    public void updateReview(long productId, Review review) {
        logger.info("Servicing request to update review with review id {}", review.getId());
        reviewInsertRepository.findByIdAndProductId(review.getId(), productId)
                .ifPresentOrElse(reviewData -> addReview(productId, review), () -> {
                    throw new InvalidReviewException("Invalid request");
                });
    }
}
