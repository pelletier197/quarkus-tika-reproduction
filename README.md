# Tika-Quarkus - Windows issue

This is a sample repository to demonstrate that a project built with Tika and Native image does not work on windows. 

## Setup
This project uses:

- Mandrel v22.3.2.0
- Quarkus-tika 2.0.0 
- Quarkus 3.0.0 (CR2)

## How to reproduce

> Step 1 and 2 are optional as they were already executed and the assets are already pushed. 

1.First part is to build the project's JAR.

```shell
./gradlew.bat build -D quarkus.package.type=uber-jar 
```

2. Then we run Mandrel with agent enabled in order to generate the native dependencies.

```shell
<path_to_mandrel>/bin/java -agentlib:native-image-agent=access-filter-file=agent-filters.json,config-output-dir=./src/main/resources -jar build/tika-quarkus-1.0.0-SNAPSHOT-runner.jar ./test-assets  
```

Here I used an agent filter because some tika dependencies are giving troubles at build time. 

This gives us what we can find in the [resources folder](./src/main/resources).

3. Finally we compile the native image.

```shell
./gradlew.bat build -D quarkus.package.type=native
```

The output error should be something like 

```

```