package com.airwallex.codechallenge.service.notification;

import org.junit.jupiter.api.Test;

public class ConsoleNotificationTest {

    private ConsoleNotification consoleNotification = new ConsoleNotification();

    //test for coverage
    @Test
    public void notify_to_console(){
        consoleNotification.notify("test");
    }

}
