package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEventLog {

    @Test
    public void TestEventLogGetInstance() {
        assertNotNull(EventLog.getInstance());
    }

    @Test
    public void TestLogEvent() {
        Event e1 = new Event("Maa chuda");
        EventLog.getInstance().logEvent(e1);
        assertEquals("EventLog{events=[" + e1.getDate() + "\n"
                +
                "Maa chuda]}", EventLog.getInstance().toString());
    }

    @Test
    public void TestClearLog() {
        EventLog.getInstance().clear();
        assertNotEquals("", EventLog.getInstance().toString());
    }

    @Test
    public void TestGetIterator() {
        assertNotNull(EventLog.getInstance().iterator());
    }
}
