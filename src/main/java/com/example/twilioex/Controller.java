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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

@RestController
public class Controller {

    private final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String SENDER_PHONE_NUMBER = "+12016694104";

    @PostMapping("/voice")
    public void incomingCall(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Say say = new Say.Builder("안녕하세요 휠차차입니다 전동보장구 충전소 지도를 보내드리오니 메시지 확인 부탁드립니다 감사합니다").language(Say.Language.KO_KR).build();
        VoiceResponse twiml = new VoiceResponse.Builder().say(say).build();

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException | IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000); //3초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //메시지 보내기
        String receiver_phone_number = request.getParameter("From");

        Message message = Message.creator(
                new PhoneNumber(receiver_phone_number),
                new PhoneNumber(SENDER_PHONE_NUMBER),
                "휠차차 지도 접속 링크\nhttps://www.numbergolf.com"
        ).create();

    }

    @PostMapping("/message")
    public void sendOutgoingMessage(){

    }

}
