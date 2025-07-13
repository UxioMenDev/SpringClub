package com.spring.club.controllers;

import com.spring.club.entities.User;
import com.spring.club.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    void setupMockMvc() {
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testRegisterView() throws Exception {
        setupMockMvc();
        var result = mockMvc.perform(get("/register"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("users/register");
    }

    @Test
    void testShowLoginView() throws Exception {
        setupMockMvc();
        var result = mockMvc.perform(get("/login"))
                .andReturn();
        assertThat(result.getResponse().getForwardedUrl()).isEqualTo("users/login");
    }

    @Test
    void testSaveUserPasswordMismatch() {
        User user = new User();
        user.setPassword("123");
        user.setPasswordConfirm("456");
        Model model = Mockito.mock(Model.class);

        UserController controller = new UserController(userService);
        String view = controller.save(user, model);

        assertThat(view).isEqualTo("users/register");
        Mockito.verify(model).addAttribute(Mockito.eq("passwordError"), Mockito.anyString());
    }
}