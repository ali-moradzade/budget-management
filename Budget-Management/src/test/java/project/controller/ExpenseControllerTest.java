package project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Long createUser(String username) throws Exception {
        String response = mockMvc.perform(post("/users")
                        .param("username", username))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return Long.parseLong(response);
    }

    private void createCategory(Long userId) throws Exception {
        mockMvc.perform(post("/categories/" + userId)
                        .param("categoryName", "food"))
                .andExpect(status().isCreated());
    }

    @Test
    void should_create_expense_successfully() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());
        createCategory(userId);

        mockMvc.perform(post("/expenses/" + userId)
                        .contentType("application/json")
                        .content("""
                                {
                                  "category": "food",
                                  "amount": 100,
                                  "description": "test",
                                  "friendIds": []
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void should_fail_when_amount_is_zero() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());
        createCategory(userId);

        mockMvc.perform(post("/expenses/" + userId)
                        .contentType("application/json")
                        .content("""
                                {
                                  "category": "food",
                                  "amount": 0,
                                  "description": "test",
                                  "friendIds": []
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_fail_when_category_missing() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());

        mockMvc.perform(post("/expenses/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "category": "",
                          "amount": 100,
                          "description": "test",
                          "friendIds": []
                        }
                        """))
                .andExpect(status().isNotFound());
    }
}