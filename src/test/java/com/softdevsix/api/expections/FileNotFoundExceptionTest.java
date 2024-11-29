package com.softdevsix.api.expections;

import com.softdevsix.api.exceptions.FileNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileNotFoundExceptionTest {
    @Test
    void testFileNotFoundExceptionMessage() {
        String expectedMessage = "The file is not found: path/to/file.txt";
        FileNotFoundException exception = new FileNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
