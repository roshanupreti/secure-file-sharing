package org.example.service.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.UnknownHostException;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SharedLinkSmsService.class})
@ExtendWith(SpringExtension.class)
class SharedLinkSmsServiceTest {

    @Autowired
    private SharedLinkSmsService sharedLinkSmsService;

    /**
     * Method under test: {@link SharedLinkSmsService#sendSMS(String, String)}
     */
    @Test
    void testSendSMS() {

        // Mock Twilio.init
        try (MockedStatic<Twilio> mockedTwilio = Mockito.mockStatic(Twilio.class)) {
            mockedTwilio.when(() -> Twilio.init(anyString(), anyString())).thenAnswer(invocation -> null);

            // Assuming sharedLinkSmsService is your class instance that calls Twilio's API
            SharedLinkSmsService sharedLinkSmsService = new SharedLinkSmsService();

            // Mock the Message.creator call separately
            try (MockedStatic<Message> mockedMessage = Mockito.mockStatic(Message.class)) {

                // Setup the mock behavior for Message.creator static method
                MessageCreator messageCreatorMock = Mockito.mock(MessageCreator.class);
                mockedMessage.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                        .thenReturn(messageCreatorMock);

                // Now, setup the mock to simulate the behavior of the .create() method call
                Mockito.when(messageCreatorMock.create()).thenReturn(Mockito.mock(Message.class));

                // Service call that triggers the static method call
                sharedLinkSmsService.sendSMS("+123456789", "Hello, world!");

                // Verification
                verify(messageCreatorMock).create();
            }
        }
    }
}
