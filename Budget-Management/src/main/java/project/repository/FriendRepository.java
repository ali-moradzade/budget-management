package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Friend;
import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserId(Long userId);

    Optional<Friend> findFriendByUserIdAndFriendName(Long userId, String friendName);

    Optional<Friend> findFriendByUserIdAndId(Long userId, Long id);

}
