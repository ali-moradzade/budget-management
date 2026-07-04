package project.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FriendE2ETest extends BaseE2ETest {

    @Test
    void addFriend_shouldPersistAndReturnList() throws Exception {

        // create user
        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        // add friend
        mockMvc.perform(post("/friends/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "friendName": "john",
                                  "friendPhone": "123"
                                }
                                """))
                .andExpect(status().isCreated());

        // get friends
        mockMvc.perform(get("/friends/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].friendName").value("john"));
    }
}