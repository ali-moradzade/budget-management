package project.dto.request;

import project.exception.BadRequestException;

public record CreateFriendRequest(
        String friendName,
        String friendPhone
) {
    public void validate() {
        if (friendName == null || friendName.isBlank()) {
            throw new BadRequestException("Friend name can not be empty");
        }
    }
}
