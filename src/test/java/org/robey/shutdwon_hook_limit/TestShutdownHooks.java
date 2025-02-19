package org.robey.shutdwon_hook_limit;

import org.junit.Ignore;
import org.junit.Test;
import org.robey.shutdown_hook_limit.MyShutdownHook;

import static org.robey.utils.Log.log;

public class TestShutdownHooks {

    @Test
    public void testSetupShutdownHook() {
        MyShutdownHook myShutdownHook = new MyShutdownHook();

        myShutdownHook.register(10);
    }


    @Test
    public void testRegisterOnce() {
        MyShutdownHook myShutdownHook = new MyShutdownHook();
        final int numHooks = 100;
        for(int i = 0; i < numHooks; i++) {
            myShutdownHook.registerOnce(() -> {
                log("Test shutdown hook firing");
            });
        }
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
