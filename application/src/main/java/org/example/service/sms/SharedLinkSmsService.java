package org.example.service.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.example.dao.CurrentLinkStatusDao;
import org.example.jparepository.PinLogRepository;
import org.example.service.validation.SharedLinkServiceValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SharedLinkSmsService implements ISmsService {

    private PinLogRepository pinLogRepository;
    private CurrentLinkStatusDao currentLinkStatusDao;
    private SharedLinkServiceValidator sharedLinkServiceValidator;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioNumber;


    /**
     * Send the provided message to the number.
     *
     * @param phoneNumber recipient phone number.
     * @param message message to send.
     */
    @Override
    public void sendSMS(
            String phoneNumber,
            String message) {

        // Generate PIN
        //final String pin = generateOTP();

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioNumber),
                message
        ).create();
    }
}

