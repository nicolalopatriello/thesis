package it.nicolalopatriello.thesis.common.utils.rsa;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.PrivateKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrivateKeyReaderTest {

    @Test
    void get_exception() {
        assertThrows(FileNotFoundException.class, () -> PrivateKeyReader.get(""));
        assertThrows(FileNotFoundException.class, () -> PrivateKeyReader.get((String) null));
        assertThrows(FileNotFoundException.class, () -> PrivateKeyReader.get((File) null));
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        assertThrows(FileNotFoundException.class, () -> PrivateKeyReader.get(file));
    }

    @Test
    void get_file() {
        String file = "src/test/resources/rsa/private_key.der";
        assertDoesNotThrow(() -> {
            PrivateKey key = PrivateKeyReader.get(file);
            assertNotNull(key);
        });
    }
}