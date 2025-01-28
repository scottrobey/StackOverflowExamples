package org.robey.shutdown_hook_limit;

import java.util.concurrent.atomic.AtomicBoolean;
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

    public void registerOnce(Runnable hook) {
        if( registered.compareAndSet(false, true) ) {
            log("Registering shutdown hook: " + hook);
            Runtime.getRuntime().addShutdownHook(new Thread(hook, "shutdown-hook-" + hook.getClass().getSimpleName()));
        } else {
            log("Already registered!");
        }

    }

    private final AtomicBoolean registered = new AtomicBoolean(false);

}
