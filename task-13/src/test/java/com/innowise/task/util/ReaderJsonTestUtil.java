package com.innowise.task.util;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ReaderJsonTestUtil {

    private ReaderJsonTestUtil() {
    }

    public static String getJsonFromFile(String path) throws IOException {
        File resource = new ClassPathResource(path).getFile();

        return new String(Files.readAllBytes(resource.toPath()));
    }

}