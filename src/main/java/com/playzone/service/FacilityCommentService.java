package com.playzone.service;

import com.playzone.api.dto.request.FacilityCommentRequest;
import com.playzone.model.FacilityComment;
import com.playzone.model.SportFacility;
import com.playzone.model.User;
import com.playzone.repository.FacilityCommentRepository;
import com.playzone.repository.SportFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityCommentService {

    private final FacilityCommentRepository commentRepository;
    private final SportFacilityRepository facilityRepository;
    private final AuthService authService;
    private final UserService userService;

    @Transactional
    public FacilityComment create(Long facilityId, FacilityCommentRequest request) {
        SportFacility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new IllegalArgumentException("Локация не найдена"));

        String username = authService.getCurrentUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        boolean exists = commentRepository.existsByUserIdAndSportFacilityId(user.getId(), facilityId);
        if (exists) {
            throw new IllegalStateException("Вы уже оставляли комментарий к этой локации");
        }

        FacilityComment comment = new FacilityComment();
        comment.setRating(request.getRating());
        comment.setText(request.getText());
        comment.setSportFacility(facility);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Transactional
    public FacilityComment update(Long commentId, FacilityCommentRequest request) {
        FacilityComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найден"));

        String username = authService.getCurrentUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("Вы можете редактировать только свой комментарий");
        }

        comment.setRating(request.getRating());
        comment.setText(request.getText());

        return commentRepository.save(comment);
    }

    public List<FacilityComment> getAllByFacility(Long facilityId) {
        return commentRepository.findAllBySportFacilityId(facilityId);
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public Double getAverageRating(Long facilityId) {
        Double avg = commentRepository.findAverageRatingByFacilityId(facilityId);
        return avg != null ? avg : 0.0;
    }
}
