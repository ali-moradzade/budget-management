package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.request.CreateFriendRequest;
import project.dto.response.FriendResponse;
import project.model.Friend;
import project.model.User;
import project.repository.FriendRepository;
import java.util.List;

@Service
@Transactional
public class FriendService {

    private final ValidationService validationService;
    private final FriendRepository friendRepository;

    public FriendService(ValidationService validationService, FriendRepository friendRepository) {
        this.validationService = validationService;
        this.friendRepository = friendRepository;
    }

    public void addFriend(Long userId, CreateFriendRequest request) {
        User user = validationService.getUserIfExists(userId);
        validationService.checkFriendNameDuplication(userId, request.friendName());
        Friend friend = new Friend(request.friendName(), request.friendPhone(), user);
        friendRepository.save(friend);
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> getUserFriends(Long userId) {
        validationService.getUserIfExists(userId);
        return friendRepository.findByUserId(userId).stream()
                .map(Friend::toDto)
                .toList();
    }
}
