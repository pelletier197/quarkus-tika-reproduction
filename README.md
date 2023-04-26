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

In powershell, run:

```shell
./gradlew.bat build -D quarkus.package.type=uber-jar 
```

2. Then we run Mandrel with agent enabled in order to generate the native dependencies.

```shell
<path_to_mandrel>/bin/java -agentlib:native-image-agent=access-filter-file=agent-filters.json,config-output-dir=./src/main/resources -jar build/tika-quarkus-1.0.0-SNAPSHOT-runner.jar ./test-assets  
```

> There are some stacktraces, being printed, it's normal. It's because some of the files are encrypted.

Here I used an agent filter because some tika dependencies are giving troubles at build time. 

This gives us what we can find in the [resources folder](./src/main/resources).

3. Finally we compile the native image.

```shell
./gradlew.bat build -D quarkus.package.type=native
```

The output error should be something like 

```
[error]: Build step io.quarkus.deployment.pkg.steps.NativeImageBuildStep#build threw an exception: java.lang.RuntimeException: Failed to build native image
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:286)
    at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
    at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.base/java.lang.reflect.Method.invoke(Method.java:568)
    at io.quarkus.deployment.ExtensionLoader$3.execute(ExtensionLoader.java:909)
    at io.quarkus.builder.BuildContext.run(BuildContext.java:282)
    at org.jboss.threads.ContextHandler$1.runWith(ContextHandler.java:18)
    at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)
    at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)
    at java.base/java.lang.Thread.run(Thread.java:833)
    at org.jboss.threads.JBossThread.run(JBossThread.java:501)
Caused by: java.lang.UnsupportedOperationException: Windows AWT integration is not ready in native-image and would result in java.lang.UnsatisfiedLinkError: no awt in java.library.path.
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep$NativeImageInvokerInfo$Builder.build(NativeImageBuildStep.java:924)
    at io.quarkus.deployment.pkg.steps.NativeImageBuildStep.build(NativeImageBuildStep.java:253)
    ... 11 more

```