package project.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryE2ETest extends BaseE2ETest {

    @Test
    void createCategory_shouldPersist() throws Exception {

        // create user
        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        // create category (IMPORTANT: request param, NOT JSON)
        mockMvc.perform(post("/categories/{userId}", userId)
                        .param("categoryName", "food")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated());

        // verify
        mockMvc.perform(get("/categories/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").value("food"));
    }

    @Test
    void create_category_should_fail_when_name_is_blank() throws Exception {

        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali4")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/categories/{userId}", userId)
                        .param("categoryName", " "))
                .andExpect(status().isBadRequest());
    }
}