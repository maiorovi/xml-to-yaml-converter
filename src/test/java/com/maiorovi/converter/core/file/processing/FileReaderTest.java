package com.maiorovi.converter.core.file.processing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileReaderTest {

    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReader("classpath:som-file.txt");
    }

    @Test
    void readsFileFromClassPath() {
        assertThat(fileReader.getInputStream()).hasContent("hello\nworld");
    }

    @Test
    void readsFileFromFileSystem() {
        fileReader = new FileReader("file:/app/Projects/open-source/xml-to-yaml-converter/src/test/resources/som-file.txt");
        assertThat(fileReader.getInputStream()).hasContent("hello\nworld");
    }
}