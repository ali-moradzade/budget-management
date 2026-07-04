package project.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpenseE2ETest extends BaseE2ETest {

    @Test
    void createExpense_shouldPersist() throws Exception {

        // user
        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        // category
        mockMvc.perform(post("/categories/{userId}", userId)
                        .param("categoryName", "food"))
                .andExpect(status().isCreated());

        // friend
        mockMvc.perform(post("/friends/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "friendName": "john",
                                  "friendPhone": "123"
                                }
                                """))
                .andExpect(status().isCreated());

        // expense
        mockMvc.perform(post("/expenses/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "category": "food",
                                  "amount": 100.0,
                                  "description": "dinner",
                                  "friendIds": []
                                }
                                """))
                .andExpect(status().isCreated());

        // verify
        mockMvc.perform(get("/expenses/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}