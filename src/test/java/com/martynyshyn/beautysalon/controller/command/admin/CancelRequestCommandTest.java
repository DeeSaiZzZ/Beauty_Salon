package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.service.Url;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelRequestCommandTest {
    @InjectMocks
    CancelRequestCommand command;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext context;

    @Test
    @SuppressWarnings("unchecked")
    void cancelRequestTest() {
        when(request.getServletContext()).thenReturn(context);

        LinkedList<Order> orders = spy(LinkedList.class);

        orders.add(new Order.Builder().build());

        when(context.getAttribute("requestList")).thenReturn(orders);

        String result = command.execute(request, response);

        verify(orders).removeFirst();
        assertEquals(Url.REDIRECT_REQUEST_FORM, result);
    }
}
