#!/bin/bash
java -version

./gradlew build test --tests ${TEST_CASE}
