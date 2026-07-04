package project.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserE2ETest extends BaseE2ETest {

    @Test
    void createUser_shouldReturnUserId() throws Exception {

        mockMvc.perform(post("/users")
                        .param("username", "ali-e2e")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue()));
    }
}