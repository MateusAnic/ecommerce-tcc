package com.zprmts.tcc.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.domain.Review;
import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.review.ReviewRequest;
import com.zprmts.tcc.ecommerce.dto.review.ReviewResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.repository.ReviewRepository;
import com.zprmts.tcc.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final PerfumeServiceImpl perfumeService;
    private final ReviewRepository reviewRepository;
    private final ObjectMapper objectMapper;
    private final UserServiceImpl usuarioService;

    @Override
    public List<ReviewResponse> getReviewsByPerfumeId(Long perfumeId) throws RegraDeNegocioException {
        Perfume perfume = perfumeService.getById(perfumeId);
        List<ReviewResponse> reviewResponseList = perfume.getReviewList()
                .stream()
                .map(review -> objectMapper.convertValue(review, ReviewResponse.class))
                .collect(Collectors.toList());
        return reviewResponseList;
    }

    @Override
    @Transactional
    public ReviewResponse addReviewToPerfume(ReviewRequest reviewRequest, Long perfumeId) throws RegraDeNegocioException {
        Perfume perfume = perfumeService.getById(perfumeId);
        Review review = objectMapper.convertValue(reviewRequest, Review.class);
        review.setAuthor(usuarioService.getUserLogado().getFirstName());
        review.setPerfume(perfume);
        List<Review> reviews = perfume.getReviewList();
        reviews.add(review);
        Double sumRating = 0.0;
        for (Review reviewObj : reviews) {
            sumRating += reviewObj.getRating();
        }
        perfume.setReviewList(reviews);
        perfume.setPerfumeRating(sumRating / reviews.size());
        reviewRepository.save(review);

        perfumeService.save(perfume);

        return objectMapper.convertValue(review, ReviewResponse.class);
    }

    public void delete(Long reviewId) throws RegraDeNegocioException {
        Review review = getById(reviewId);
        Perfume perfume = review.getPerfume();
        perfume.getReviewList().remove(review);
        review.setPerfume(null);
        reviewRepository.delete(review);
        perfumeService.save(perfume);
    }

    private Review getById(Long id) throws RegraDeNegocioException {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Review não encontrado"));
    }
}
