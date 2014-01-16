package me.predatorray.jdbc.datasource;

import org.junit.Assert;
import org.junit.Test;

public class RoundRobinTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZero() {
        new RoundRobin(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNonPositiveNumber() {
        new RoundRobin(-1);
    }

    @Test
    public void testRoundRobin() {
        RoundRobin roundRobin = new RoundRobin(3);
        Assert.assertEquals(0, roundRobin.next());
        Assert.assertEquals(1, roundRobin.next());
        Assert.assertEquals(2, roundRobin.next());
        Assert.assertEquals(0, roundRobin.next());
    }
}
