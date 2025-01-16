package org.robey.shutdown_hook_limit;

import java.util.concurrent.atomic.AtomicInteger;

import static org.robey.utils.Log.log;
import static org.robey.utils.Log.logError;

public class MyShutdownHook {

    public void register(int numberOfHooks) {
        for(int i = 0; i < numberOfHooks; i++) {
            final AtomicInteger atomI = new AtomicInteger(i);
            log("Registering Shutdown Hook #" + atomI);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log("Shutdown Hook #" + atomI + " Firing!");
                } catch (Throwable e) {
                    logError("Error in Shutdown Hook", e);
                }
            }));
        }
    }
}
