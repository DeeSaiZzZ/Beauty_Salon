package com.martynyshyn.beautysalon.controller.command;

import com.martynyshyn.beautysalon.service.Url;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class ErrorCommandTest {

    @InjectMocks
    private ErrorCommand command;

    @Test
    void testErrorCommand() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String result = command.execute(request, response);
        assertEquals(Url.ERROR_PAGE, result);
    }

}
