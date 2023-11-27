package com.electrica.Electrica.RegisterControler;

import com.electrica.Electrica.Entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class WebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        // process the message, for example, check the limit
        // and send a message if the limit is exceeded
        return new OutputMessage(message.getFrom(), message.getText(), new Date());
    }
}
