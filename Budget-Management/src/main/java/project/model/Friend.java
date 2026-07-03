package project.model;

import jakarta.persistence.*;
import project.dto.response.FriendResponse;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "friend_name", nullable = false)
    private String friendName;

    @Column(name = "friend_phone")
    private String friendPhone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Friend() {
    }

    public Friend(String friendName, String friendPhone, User user) {
        this.friendName = friendName;
        this.friendPhone = friendPhone;
        this.user = user;
    }

    public FriendResponse toDto() {
        return new FriendResponse(id, friendName, friendPhone);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPhone() {
        return friendPhone;
    }

    public void setFriendPhone(String friendPhone) {
        this.friendPhone = friendPhone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
