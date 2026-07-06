package project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import project.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long createUser(String username) throws Exception {
        String response = mockMvc.perform(post("/users")
                        .param("username", username))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return Long.parseLong(response);
    }

    @Test
    void should_create_category_successfully() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());

        mockMvc.perform(post("/categories/" + userId)
                        .param("categoryName", "food"))
                .andExpect(status().isCreated());
    }

    @Test
    void should_fail_when_category_name_is_blank() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());

        mockMvc.perform(post("/categories/" + userId)
                        .param("categoryName", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_get_categories_empty_initially() throws Exception {
        Long userId = createUser("ali_" + System.nanoTime());

        mockMvc.perform(get("/categories/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}