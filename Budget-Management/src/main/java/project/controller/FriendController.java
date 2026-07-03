package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.dto.request.CreateFriendRequest;
import project.dto.response.FriendResponse;
import project.service.FriendService;
import java.util.List;

@Tag(name = "Friends", description = "Friend management endpoints")
@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @Operation(summary = "Create a new friend")
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriend(@PathVariable Long userId, @RequestBody CreateFriendRequest request) {
        request.validate();
        friendService.addFriend(userId, request);
    }

    @Operation(summary = "Get list of your friends")
    @GetMapping("/{userId}")
    public List<FriendResponse> getUserFriends(@PathVariable Long userId) {
        return friendService.getUserFriends(userId);
    }
}
