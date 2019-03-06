package com.jpop.reviewservice.controller;

import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.model.dto.ReviewDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReviewServiceController {

    @GetMapping(value = "/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviews(@PathVariable long productId) {
        return null;
    }

    @PostMapping(value = "/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> addReview(@PathVariable long productId, @RequestBody ReviewDTO review) {
        return null;
    }

    @PutMapping(value = "/{productId}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId) {
        return null;
    }

    @DeleteMapping(value = "/{productId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId) {
        return null;
    }
}
