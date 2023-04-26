# Tika-Quarkus

This is a sample repository to demonstrate that a project built with Tika and Native image does not fully work. 

## How to reproduce

1.First part is to build the project's JAR.

```shell
./gradlew build -Dquarkus.package.type=uber-jar 
```

2. Then we run Mandrel with agent enabled in order to generate the native dependencies.

```shell
<path_to_mandrel>/bin/java -agentlib:native-image-agent=access-filter-file=agent-filters.json,config-output-dir=./src/main/resources -jar build/tika-quarkus-1.0.0-SNAPSHOT-runner.jar ./test-assets  
```

Here I used an agent filter because some tika dependencies are giving troubles at build time. 

This gives us what we can find in the [resources folder](./src/main/resources).

Then we compile the native image.

```shell
./gradlew build -Dquarkus.package.type=native
```

Then we run the output executable.

```shell

```