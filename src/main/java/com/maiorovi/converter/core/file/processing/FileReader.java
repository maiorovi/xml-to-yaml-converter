package com.maiorovi.converter.core.file.processing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileReader {

    private final String fileName;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        String[] parts = fileName.split(":");

        switch (parts[0]) {
            case "classpath":
                return loadFromClasspath(parts[1]);
            case "file":
                return loadFromFileSystem(parts[1]);

//            default:
//                return loadFromClasspath(parts[1]);
        }

        return null;
    }

    private InputStream loadFromClasspath(String path) {
        return FileReader.class.getClassLoader().getResourceAsStream(path);
    }

    private InputStream loadFromFileSystem(String path) {
        try {
            return new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
