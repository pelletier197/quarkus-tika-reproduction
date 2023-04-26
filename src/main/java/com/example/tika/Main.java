package com.example.tika;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@QuarkusMain
public class Main {
    public static void main(String[] args) {
        Quarkus.run(TikaApplication.class, args);
    }

    public static class TikaApplication implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            if (args.length == 0) {
                System.out.println("You must provide a directory or file path to scan as a first unnamed argument");
                System.exit(1);
            }
            scanAllFiles(Paths.get(args[0]));
            return 0;
        }
    }


    private static void scanAllFiles(Path target) throws IOException {
        AtomicInteger processedFiles = new AtomicInteger();
        Files.walk(target)
                .parallel()
                .filter(Files::isRegularFile)
                .forEach((path) -> {
                    System.out.println("-----------------");
                    System.out.printf("Processing %s%n", path);
                    try {
                        consumeFileWithTika(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    processedFiles.addAndGet(1);
                });
        System.out.printf("Done! %s files were processed%n", processedFiles.get());
    }

    private static void consumeFileWithTika(Path path) throws IOException {
        Tika tika = new Tika();
        Metadata metadata = new Metadata();
        final Reader reader = tika.parse(new FileInputStream(path.toFile()), metadata);
        char[] data = new char[8192];

        System.out.println("Used parser: " + metadata.get("X-TIKA:Parsed-By"));

        int total = 0;

        // Consumes all reader
        while (true) {
            int size = reader.read(data, 0, data.length);
            if (size == -1) {
                System.out.println("Extracted chars: "+ total);
                break;
            } else {
                total += size;

            }
        }
    }
}
