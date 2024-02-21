package org.example.service.sms;

public interface ISmsService {

    /**
     * Send the provided message to the number.
     *
     * @param phoneNumber recipient phone number.
     * @param message message to send.
     */
    void sendSMS(String phoneNumber, String message);
}
