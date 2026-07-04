package project.e2e;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExpenseParticipantE2ETest extends BaseE2ETest {

    @Test
    void getDebts_shouldReturnData() throws Exception {

        String userId = mockMvc.perform(post("/users")
                        .param("username", "ali"))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/expense-participants/{userId}/debts", userId))
                .andExpect(status().isOk());
    }
}