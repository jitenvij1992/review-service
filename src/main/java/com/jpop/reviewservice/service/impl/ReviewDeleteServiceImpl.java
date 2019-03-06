package com.jpop.reviewservice.service.impl;

import com.jpop.reviewservice.dao.ReviewDeleteRepository;
import com.jpop.reviewservice.service.ReviewDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewDeleteServiceImpl implements ReviewDeleteService {

    @Autowired
    ReviewDeleteRepository reviewDeleteRepository;

    @Override
    public void deleteReview(long id, long productId) {
        reviewDeleteRepository.deleteByIdAndProductId(id, productId);
    }
}
