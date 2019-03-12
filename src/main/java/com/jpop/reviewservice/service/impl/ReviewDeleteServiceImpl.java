package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewDeleteRepository;
import com.jpop.reviewservice.exception.InvalidReviewException;
import com.jpop.reviewservice.service.ReviewDeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewDeleteServiceImpl implements ReviewDeleteService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewDeleteServiceImpl.class);

    @Autowired
    private ReviewDeleteRepository reviewDeleteRepository;

    @Override
    public void deleteReview(long id, long productId) {
        logger.info("Servicing request to delete review having review id {}", id);
        reviewDeleteRepository.findByIdAndProductId(id, productId)
                .ifPresentOrElse(review -> reviewDeleteRepository
                        .deleteByIdAndProductId(id, productId), () -> {
                    throw new InvalidReviewException("Invalid Request");
                });
        logger.info("Successfully deleted review");
    }
}
