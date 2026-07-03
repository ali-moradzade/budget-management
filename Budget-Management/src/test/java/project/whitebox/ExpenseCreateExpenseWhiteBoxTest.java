package project.whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.model.Friend;
import project.model.User;
import project.repository.ExpenseRepository;
import project.repository.FriendRepository;
import project.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseCreateExpenseWhiteBoxTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId;
    private Long friendId1;
    private Long friendId2;

    @BeforeEach
    void setup() {
        expenseRepository.deleteAll();
        friendRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testuser");
        user = userRepository.save(user);
        userId = user.getId();

        Friend f1 = new Friend();
        f1.setFriendName("friend1");
        f1.setUser(user);
        f1 = friendRepository.save(f1);
        friendId1 = f1.getId();

        Friend f2 = new Friend();
        f2.setFriendName("friend2");
        f2.setUser(user);
        f2 = friendRepository.save(f2);
        friendId2 = f2.getId();
    }

    @Test
    void createExpense_withoutParticipants_shouldPass() throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("amount", 100);
        body.put("description", "test");
        body.put("category", null);
        body.put("participants", null);

        mockMvc.perform(post("/expenses/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void createExpense_withParticipants_shouldSplitExpense() throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("amount", 100);
        body.put("description", "test");
        body.put("category", null);
        body.put("participants", List.of(friendId1, friendId2));

        mockMvc.perform(post("/expenses/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void createExpense_invalidUser_shouldFail() throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("amount", 100);
        body.put("description", "test");
        body.put("category", null);
        body.put("participants", null);

        mockMvc.perform(post("/expenses/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }
}