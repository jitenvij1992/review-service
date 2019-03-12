package com.jpop.reviewservice.dao;

import com.jpop.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDetailRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(long productId);
}
