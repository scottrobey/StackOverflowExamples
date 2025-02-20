#!/bin/bash
java -version

javac Main.java
java Main

echo "cgroup cpu.max: $(cat /sys/fs/cgroup/cpu.max)"

echo "nproc: $(nproc)"
