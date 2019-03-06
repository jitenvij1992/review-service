package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewDetailRepository;
import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.service.ReviewDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewDetailServiceImpl implements ReviewDetailService {

    @Autowired
    ReviewDetailRepository reviewDetailRepository;

    @Override
    public List<Review> getAllReviews(long productId) {
        return reviewDetailRepository.findAllByProductId(productId);
    }
}
