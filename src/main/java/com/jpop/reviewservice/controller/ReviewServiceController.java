package com.jpop.reviewservice.controller;

import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.model.dto.ReviewDTO;
import com.jpop.reviewservice.service.ReviewDeleteService;
import com.jpop.reviewservice.service.ReviewDetailService;
import com.jpop.reviewservice.service.ReviewInsertService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewServiceController {

    private ReviewInsertService reviewInsertService;
    private ReviewDetailService reviewDetailService;
    private ReviewDeleteService reviewDeleteService;

    @Autowired
    public ReviewServiceController(ReviewInsertService reviewInsertService, ReviewDetailService reviewDetailService, ReviewDeleteService reviewDeleteService) {
        this.reviewInsertService = reviewInsertService;
        this.reviewDetailService = reviewDetailService;
        this.reviewDeleteService = reviewDeleteService;
    }

    @GetMapping(value = "/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Review>> getReviews(@PathVariable long productId) {
        return ResponseEntity.ok(reviewDetailService.getAllReviews(productId));
    }

    @PostMapping(value = "/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> addReview(@PathVariable long productId, @RequestBody ReviewDTO review) {
        ModelMapper modelMapper = new ModelMapper();
        Review mappedReview = modelMapper.map(review, Review.class);
        Review savedReview = reviewInsertService.addReview(productId, mappedReview);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(savedReview.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(savedReview);
    }

    @PutMapping(value = "/{productId}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId, @RequestBody ReviewDTO review) {
        ModelMapper modelMapper = new ModelMapper();
        Review mappedReview = modelMapper.map(review, Review.class);
        mappedReview.setId(reviewId);

        return null;
    }

    @DeleteMapping(value = "/{productId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId) {
        reviewDeleteService.deleteReview(reviewId, productId);
        return null;
    }
}
