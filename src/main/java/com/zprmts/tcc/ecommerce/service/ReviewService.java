package com.zprmts.tcc.ecommerce.service;

import com.zprmts.tcc.ecommerce.domain.Review;
import com.zprmts.tcc.ecommerce.dto.review.ReviewRequest;
import com.zprmts.tcc.ecommerce.dto.review.ReviewResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> getReviewsByPerfumeId(Long perfumeId) throws RegraDeNegocioException;

    ReviewResponse addReviewToPerfume(ReviewRequest reviewRequest, Long perfumeId) throws RegraDeNegocioException;
}
