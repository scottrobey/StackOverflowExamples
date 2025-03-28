package org.robey.utils;

import org.apache.commons.lang3.time.StopWatch;

public class Time {

    @FunctionalInterface public interface RunnableThatThrows {
        void run() throws Exception;
    }
    public static String timeIt(RunnableThatThrows operationToTime) throws Exception {
        final StopWatch watch = StopWatch.createStarted();
        operationToTime.run();
        watch.stop();
        return watch.formatTime();
    }
}
