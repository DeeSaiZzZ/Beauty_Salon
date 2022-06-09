package com.martynyshyn.beautysalon.controller.command.common;

import com.martynyshyn.beautysalon.controller.command.common.ChangeLanguageCommand;
import com.martynyshyn.beautysalon.service.Url;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeLanguageCommandTest {

    @InjectMocks
    private ChangeLanguageCommand command;

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Test
    void testChangeLang() {
        when(request.getParameter("lang")).thenReturn("en");

        String result = command.execute(request, response);

        assertEquals(Url.REDIRECT_MASTER_LIST_PAGE, result);
        verify(response, times(1)).addCookie(any(Cookie.class));
    }

}
