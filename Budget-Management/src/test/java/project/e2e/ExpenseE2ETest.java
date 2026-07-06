package project.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpenseE2ETest extends BaseE2ETest {

    // -------------------------
    // helpers
    // -------------------------
    private String createUser(String username) throws Exception {
        return mockMvc.perform(post("/users")
                        .param("username", username)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void createCategory(String userId, String name) throws Exception {
        mockMvc.perform(post("/categories/{userId}", userId)
                        .param("categoryName", name))
                .andExpect(status().isCreated());
    }

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
                                  "participants": []
                                }
                                """))
                .andExpect(status().isCreated());

        // verify
        mockMvc.perform(get("/expenses/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_return_400_when_username_blank() throws Exception {
        mockMvc.perform(post("/users")
                        .param("username", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_category_name_blank() throws Exception {

        String userId = createUser("ali");

        mockMvc.perform(post("/categories/{userId}", userId)
                        .param("categoryName", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_when_expense_amount_invalid() throws Exception {

        String userId = createUser("ali");
        createCategory(userId, "food");

        mockMvc.perform(post("/expenses/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "category": "food",
                                  "amount": 0,
                                  "description": "invalid",
                                  "friendIds": []
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_404_when_user_not_found() throws Exception {

        mockMvc.perform(get("/expenses/{userId}", 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_404_when_category_not_found() throws Exception {

        String userId = createUser("ali");

        mockMvc.perform(post("/expenses/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "category": "unknown",
                                  "amount": 100,
                                  "description": "test",
                                  "participants": []
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void get_expenses_should_return_empty_list_when_none_exist() throws Exception {

        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/expenses/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void create_expense_should_fail_when_amount_is_null() throws Exception {

        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali3")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/expenses/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "category": "food",
                          "amount": null,
                          "description": "test",
                          "friendIds": []
                        }
                    """))
                .andExpect(status().isBadRequest());
    }
}