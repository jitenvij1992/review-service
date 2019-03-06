package com.jpop.reviewservice.dao;

import com.jpop.reviewservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInsertRepository extends JpaRepository<Review, Long> {
}
