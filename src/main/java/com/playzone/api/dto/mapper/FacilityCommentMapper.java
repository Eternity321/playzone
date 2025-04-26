package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.FacilityCommentRequest;
import com.playzone.api.dto.response.FacilityCommentResponse;
import com.playzone.model.FacilityComment;
import com.playzone.model.SportFacility;
import com.playzone.model.User;
import org.springframework.stereotype.Component;

@Component
public class FacilityCommentMapper {

    public FacilityComment toEntity(FacilityCommentRequest request, SportFacility facility, User user) {
        FacilityComment comment = new FacilityComment();
        comment.setRating(request.getRating());
        comment.setText(request.getText());
        comment.setSportFacility(facility);
        comment.setUser(user);
        return comment;
    }

    public FacilityCommentResponse toResponse(FacilityComment comment) {
        FacilityCommentResponse response = new FacilityCommentResponse();
        response.setId(comment.getId());
        response.setRating(comment.getRating());
        response.setText(comment.getText());
        response.setUserNickname(comment.getUser().getNickname());
        response.setUserId(comment.getUser().getId());
        response.setUserAvatarKey(comment.getUser().getUserAvatarKey());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }
}
