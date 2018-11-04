package be.kdg.processor.controllers.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginWithInvalidUserThenUnauthenticated() throws Exception {

        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    @Test
    public void testAccessUnsecuredResource() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAccessSecuredResourceAuthenticated() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testAccessSecuredResourceUnAuthorizedUserThenRedirects() throws Exception {

        mockMvc.perform(get("/settings"))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));
    }
}
