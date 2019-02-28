# k8s-java-client
This project, a work in progress, implements a Java sdk client for Kubernetes. It's intended to be running within a container to perform the same tasks that kubectl performs outside.

The only kubectl command  that is currently functional is the exec command


## Setup 
Clone the project and run the executable in the build folder in the following way:

java -jar build/libs/k8s-client-0.0.1.jar  exec <param1> <param2> ...

You can also build from the source by running

`./gradlew booJar`

