package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.gui.CancelAppGUI;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class TestEvent {
    private Event e1, e2, e3;

    @BeforeEach
    public void setUp() {
        this.e1 = new Event("Appointments added");
        this.e2 = new Event("Patients added");
    }

    @Test
    public void TestConstructor() {
        assertEquals("Appointments added", e1.getDescription());
        assertEquals("Patients added", e2.getDescription());
    }

    @Test
    public void TestGetDate() {
        assertEquals(Calendar.getInstance().getTime().toString(), e1.getDate().toString());
        assertEquals(Calendar.getInstance().getTime().toString(), e2.getDate().toString());
    }

    @Test
    public void TestEqualsNullObject() {
        assertFalse(e1.equals(null));
    }

    @Test
    public void TestEqualsDifferentData() {
        assertFalse(e1.equals("Maa chuda"));
    }

    @Test
    public void TestEqualsFalseDifferentDescriptionSameTime() {
        assertFalse(e1.equals(e2));
    }

    @Test
    public void TestEqualsSameDescriptionDifferentTime() throws InterruptedException {
        Thread.sleep(1000);
        Event e3 = new Event(e1.getDescription());
        assertFalse(e1.equals(e3));
    }

    @Test
    public void TestEqualsSameDescriptionSameTime(){
        Event e3 = new Event(e1.getDescription());
        System.out.println(e1.getDate());
        System.out.println(e1.getDescription());
        System.out.println(e3.getDate());
        System.out.println(e3.getDescription());
        assertNotEquals(e1, e3);
    }

    @Test
    public void TestDifferentDescriptionDifferentTime() throws InterruptedException {
        Thread.sleep(1000);
        Event e3 = new Event(e2.getDescription());
        assertFalse(e1.equals(e3));
    }

    @Test
    public void TestHashCode() {
        assertNotEquals(-1052445337, e1.hashCode());
    }

    @Test
    public void TestToString() {
        assertEquals(e1.getDate()+ "\n" +
                "Appointments added", e1.toString());
    }
}
