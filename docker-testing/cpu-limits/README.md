# Testing CPU Limits in Different Versions of Java

This project has a Main class and docker compose file to demonstrate the difference in behavior of the JVM when it comes to it's container awareness.

Java 8 and earlier versions presumably, don't have support for Linux cgroups, so the JDK classes won't respect things like cgroup CPU limits.

As I understand it, starting in Java 11 and later, the JDK has become container-aware and will respect cgroup CPU Limits.

See: https://stackoverflow.com/questions/75233530/java-lang-runtime-getruntime-availableprocessors-difference-beween-java-8

## Run Me
You can run the test if you have Docker installed by simply running: `docker compose up`

Edit the `docker-compose.yml` file to test different Java Docker images.

## Test Results

Here's the results of testing OpenJDK versions: 7, 8, 11, 17, and latest.

Notice how Java 7 and 8 do NOT respect cpu.max settings and return 2 for the call to: `Runtime.getRuntime().availableProcessors()` even though cpu.max has been set to 1 by adding CPU limit: 1
in the compose file


```
# image: openjdk:7-jdk
java-test-1  | java version "1.7.0_221"
java-test-1  | OpenJDK Runtime Environment (IcedTea 2.6.18) (7u221-2.6.18-1~deb8u1)
java-test-1  | OpenJDK 64-Bit Server VM (build 24.221-b02, mixed mode)
java-test-1  | JVM Available CPUs: 2
java-test-1  | cgroup cpu.max: 100000 100000
java-test-1  | nproc: 2

# image: openjdk:8
java-test-1  | openjdk version "1.8.0_342"
java-test-1  | OpenJDK Runtime Environment (build 1.8.0_342-b07)
java-test-1  | OpenJDK 64-Bit Server VM (build 25.342-b07, mixed mode)
java-test-1  | JVM Available CPUs: 2
java-test-1  | cgroup cpu.max: 100000 100000
java-test-1  | nproc: 2

# image: openjdk:11
java-test-1  | openjdk version "11.0.16" 2022-07-19
java-test-1  | OpenJDK Runtime Environment 18.9 (build 11.0.16+8)
java-test-1  | OpenJDK 64-Bit Server VM 18.9 (build 11.0.16+8, mixed mode, sharing)
java-test-1  | JVM Available CPUs: 1
java-test-1  | cgroup cpu.max: 100000 100000
java-test-1  | nproc: 2

# image: openjdk:17
java-test-1  | openjdk version "17.0.2" 2022-01-18
java-test-1  | OpenJDK Runtime Environment (build 17.0.2+8-86)
java-test-1  | OpenJDK 64-Bit Server VM (build 17.0.2+8-86, mixed mode, sharing)
java-test-1  | JVM Available CPUs: 1
java-test-1  | cgroup cpu.max: 100000 100000
java-test-1  | nproc: 2

# image: openjdk:latest
java-test-1  | openjdk version "18.0.2.1" 2022-08-18
java-test-1  | OpenJDK Runtime Environment (build 18.0.2.1+1-1)
java-test-1  | OpenJDK 64-Bit Server VM (build 18.0.2.1+1-1, mixed mode, sharing)
java-test-1  | JVM Available CPUs: 1
java-test-1  | cgroup cpu.max: 100000 100000
java-test-1  | nproc: 2

```
