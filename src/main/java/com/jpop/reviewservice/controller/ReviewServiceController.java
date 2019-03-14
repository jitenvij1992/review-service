package com.jpop.reviewservice.controller;

import com.jpop.reviewservice.model.Review;
import com.jpop.reviewservice.model.dto.ReviewDTO;
import com.jpop.reviewservice.service.ReviewDeleteService;
import com.jpop.reviewservice.service.ReviewDetailService;
import com.jpop.reviewservice.service.ReviewInsertService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ReviewServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceController.class);


    private ReviewInsertService reviewInsertService;
    private ReviewDetailService reviewDetailService;
    private ReviewDeleteService reviewDeleteService;

    @Autowired
    public ReviewServiceController(ReviewInsertService reviewInsertService, ReviewDetailService reviewDetailService, ReviewDeleteService reviewDeleteService) {
        this.reviewInsertService = reviewInsertService;
        this.reviewDetailService = reviewDetailService;
        this.reviewDeleteService = reviewDeleteService;
    }


    @ApiOperation(value = "Get all reviews", notes = "This will be used to get all the reviews from inventory based on the product Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review found in inventory")
    })
    @GetMapping(value = "/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Review>> getReviews(@PathVariable long productId) {
        logger.info("Received request to get reviews having product id: {}", productId);
        return ResponseEntity.ok(reviewDetailService.getAllReviews(productId));
    }

    //hateoas-example
//    @ApiOperation(value = "Get all reviews", notes = "This will be used to get all the reviews from inventory based on the product Id")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Review found in inventory")
//    })
//    @GetMapping(value = "/{productId}/reviews", produces = "application/hal+json")
//    public ResponseEntity getReviews(@PathVariable long productId) {
//        logger.info("Received request to get reviews having product id: {}", productId);
//        List<Review> reviews = reviewDetailService.getAllReviews(productId);
//        Link link =linkTo(methodOn(ReviewServiceController.class).getReviews(productId)).withRel("reviews");
//        Resources<Review> result = new Resources<>(reviews,link);
//        System.out.println(result);
//        return ResponseEntity.ok(result);
//    }

    @ApiOperation(value = "Insert new reviews", notes = "This will be used to add reviews in inventory")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Review added in inventory", responseHeaders = @ResponseHeader(name = "location", description = "location of created resources", response = URI.class))
    })
    @PostMapping(value = "/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addReview(@PathVariable long productId, @Valid @RequestBody ReviewDTO review, @ApiIgnore Errors errors) {
        logger.info("Received request to insert review having product id {} and payload {}", productId, review);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(createErrorString(errors), HttpStatus.BAD_REQUEST);
        }
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

    @ApiOperation(value = "Update review by ID", notes = "This will be used to update the review by using review ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review updated in inventory"),
            @ApiResponse(code = 400, message = "Invalid ID supplied")
    })
    @PutMapping(value = "/{productId}/reviews/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId, @Valid @RequestBody ReviewDTO review, @ApiIgnore Errors errors) {
        logger.info("Received request to update the review having product id {}, review id {} and payload {}", productId, reviewId, review);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(createErrorString(errors), HttpStatus.BAD_REQUEST);
        }
        ModelMapper modelMapper = new ModelMapper();
        Review mappedReview = modelMapper.map(review, Review.class);
        mappedReview.setId(reviewId);
        reviewInsertService.updateReview(productId, mappedReview);
        return ResponseEntity.ok("Successfully updated review");
    }

    @ApiOperation(value = "Delete review by ID", notes = "This will be used to delete the review by using review ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Review deleted from inventory"),
            @ApiResponse(code = 400, message = "Invalid ID supplied")
    })
    @DeleteMapping(value = "/{productId}/reviews/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("productId") long productId, @PathVariable("reviewId") long reviewId) {
        logger.info("Received request to delete the review having product id {} and review id {}", productId, reviewId);
        reviewDeleteService.deleteReview(reviewId, productId);
        return ResponseEntity.noContent().build();
    }

    private String createErrorString(Errors errors) {
        return errors.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining(","));
    }
}
