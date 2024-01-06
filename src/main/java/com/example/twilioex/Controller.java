package com.example.twilioex;

import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

    @PostMapping("/voice")
    public void incomingCall(HttpServletResponse response){
        Say say = new Say.Builder("Hello world! 안녕하세요").build();
        VoiceResponse twiml = new VoiceResponse.Builder().say(say).build();
        response.setContentType("text/xml");
        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException | IOException e) {
            e.printStackTrace();
        }
    }

}
