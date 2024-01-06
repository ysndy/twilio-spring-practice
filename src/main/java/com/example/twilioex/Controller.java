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
    public void incomingCall(HttpServletRequest request, HttpServletResponse response){
        Say say = new Say.Builder("안녕하세요 휠차차입니다. 전동휠체어 지도를 보내드리니 메시지를 확인해주세요. 감사합니다.").language(Say.Language.KO_KR).build();
        VoiceResponse twiml = new VoiceResponse.Builder().say(say).build();

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        //메시지 보내기
        System.out.println(request.toString());
        System.out.println(request.getParameter("From"));

        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException | IOException e) {
            e.printStackTrace();
        }
    }

}
