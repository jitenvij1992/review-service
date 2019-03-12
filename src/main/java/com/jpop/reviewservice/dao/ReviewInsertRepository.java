package com.jpop.reviewservice.dao;

import com.jpop.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewInsertRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByIdAndProductId(long id, long productId);
}
