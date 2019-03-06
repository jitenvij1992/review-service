package com.jpop.reviewservice.dao;

import com.jpop.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewDeleteRepository extends JpaRepository<Review, Long> {

    @Transactional
    void deleteByIdAndProductId(long reviewId, long productId);
}
