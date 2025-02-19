# Limitations of the Java Shutdown Hook

This project is designed to try to test and determine the limit of the number of shutdown hooks that can be 
registered with the Java JVM.

For reference, a shutdown hook is a bit of logic that gets executed by the JVM when the JVM is terminating gracefully. That 
is, when the JVM gets a SIGTERM signal telling it to shut down, it will execute the shutdown hooks that have previously been
registered. Obviously if the JVM is killed forcefully (`kill -9`) then it won't have the chance to execute shutdown hooks.

Javadocs for JVM Shutdown Hook: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Runtime.html#addShutdownHook(java.lang.Thread)

Design: https://docs.oracle.com/javase/8/docs/technotes/guides/lang/hook-design.html

There are various posts online that talk about the theoretical limit to the number of shutdown hooks that can be registered 
with the JVM. Here we'll try to determine what the actual limits are and how the JVM behaves when it reaches those limits, 
if there is in fact a limit.

Depends on:
* Resources of the environment
* Java version
* OS and version
* ???

If we find a limit, then what's a better solution than just creating a bunch of separate shutdown hooks?

What about JVM features like virtual threads? Are we hitting limits for the sheer number of threads that the JVM can support?
Try the same test but with creating just regular threads, not JVM hooks

How do we test this? 
Since we can't terminate the JVM in a unit test, the best way to test the hooks actually firing is in Docker.
Docker can also help us control resources to determine if resources affect shutdown hook limitations