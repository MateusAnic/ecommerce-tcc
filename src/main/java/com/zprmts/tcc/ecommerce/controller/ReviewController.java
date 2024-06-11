package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.dto.review.ReviewRequest;
import com.zprmts.tcc.ecommerce.dto.review.ReviewResponse;
import com.zprmts.tcc.ecommerce.service.Impl.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "Review")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @GetMapping("/perfume/{idPerfume}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByPerfumeId(@PathVariable Long idPerfume) throws Exception {
        return new ResponseEntity<>(reviewService.getReviewsByPerfumeId(idPerfume), HttpStatus.OK);
    }

    @PostMapping("/perfume/{idPerfume}")
    public ResponseEntity<ReviewResponse> addReviewToPerfume(@NotNull @PathVariable Long idPerfume,
                                                             @Valid @RequestBody ReviewRequest reviewRequest) throws Exception {
        return new ResponseEntity<>(reviewService.addReviewToPerfume(reviewRequest, idPerfume), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) throws Exception {
        reviewService.delete(id);
        return ResponseEntity.ok().build();
    }
}
