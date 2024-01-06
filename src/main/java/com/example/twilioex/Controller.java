package com.example.twilioex;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

    private final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String SENDER_PHONE_NUMBER = "+12016694104";

    @PostMapping("/voice")
    public void incomingCall(HttpServletRequest request, HttpServletResponse response){
        Say say = new Say.Builder("안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요").language(Say.Language.KO_KR).build();
        VoiceResponse twiml = new VoiceResponse.Builder().say(say).build();

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException | IOException e) {
            e.printStackTrace();
        }

        //메시지 보내기
        String receiver_phone_number = request.getParameter("From");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(receiver_phone_number),
                new PhoneNumber(SENDER_PHONE_NUMBER),
                "휠차차 지도 접속 링크\nhttps://www.numbergolf.com"
        ).create();

    }

}
