package org.robey.shutdwon_hook_limit;

import org.junit.Ignore;
import org.junit.Test;
import org.robey.shutdown_hook_limit.MyShutdownHook;

public class TestShutdownHooks {

    @Test
    public void testSetupShutdownHook() {
        MyShutdownHook myShutdownHook = new MyShutdownHook();

        myShutdownHook.register(10);
    }

    // comment out this Ignore to run the test
    @Ignore("This tests the actual shutdown hooks by exiting the JVM so run it individually but not as part of the build process")
    @Test
    public void testLotsOfShutdownHooksFiring() {
        int num = 10_000_000;
        MyShutdownHook myShutdownHook = new MyShutdownHook();

        myShutdownHook.register(num);

        // you shouldn't really do this in a unit test but it works for specialized testing
        System.exit(0);
    }
}
