package oop1;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReturnScooterServiceTest {
    DatabaseDelegate databaseDelegate = mock();
    ChargeDelegate chargeDelegate = mock();

    ReturnScooterService service = new ReturnScooterService(databaseDelegate, chargeDelegate);

    @Test
    void itJustRuns() {
        long clientId = 1L;
        long scooterId = 10L;
        Position position = new Position();
        int rentDuration = 60;
        float batteryLevel = 1.0f;
        Object[] scooterData = {"not_fast", 10.0f, 30.0f};
        int clientCredit = 100;
        boolean clientWithImmediatePayment = true;
        int immediateTransactionsCounter = 1;

        service.returnScooter(clientId, scooterId, position, rentDuration, batteryLevel, scooterData, clientCredit, clientWithImmediatePayment, immediateTransactionsCounter);

        verify(databaseDelegate).saveInDatabase(anyInt(),anyFloat(), anyBoolean(), anyInt());
        verify(chargeDelegate).chargeClient(anyLong(), anyFloat());
    }

}