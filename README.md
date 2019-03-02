# k8s-java-client
This project, a work in progress, implements a Java sdk client for Kubernetes. It's intended to be running within a container to perform the same tasks that kubectl performs outside.

The only kubectl command  that is currently functional is the exec command


## Setup 
Clone the project and run the executable in the build folder in the following way:

java -jar build/libs/k8s-client-0.0.1.jar  exec <param1> <param2> ...

You can also build from the source by running

`./gradlew booJar`

## Setup 

To open a shell into the container `jnlp`, in the pod `agent-pod-pzxs5` in the default namespace
`java -jar build/libs/k8s-client-0.0.1.jar   exec \
--ns=default \
--pod=agent-pod-pzxs5 \
--con=jnlp \
--cmd=sh `

To log into docker from the container `dind` in the same pod as above
`java -jar build/libs/k8s-client-0.0.1.jar  exec \
--ns=default \
--pod=agent-pod-pzxs5 \
--con=dind \
--cmd=docker --cmd=login --cmd=--username --cmd=efossi --cmd=--password --cmd=mypwd `

To build a Docker image on the container `dind` in the same pod as above
`java -jar build/libs/k8s-client-0.0.1.jar  exec \
--ns=default \
--pod=agent-pod-pzxs5 \
--con=dind \
--cmd=docker --cmd=login --cmd=--username --cmd=efossi --cmd=--password --cmd=mypwd `