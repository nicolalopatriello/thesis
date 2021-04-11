package it.nicolalopatriello.thesis.common.utils.rsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("public key reader Utils")
class PublicKeyReaderTest {

    @Test
    void get_exception() {
        assertThrows(FileNotFoundException.class, () -> PublicKeyReader.get(""));
        assertThrows(FileNotFoundException.class, () -> PublicKeyReader.get((String) null));
        assertThrows(FileNotFoundException.class, () -> PublicKeyReader.get((File) null));
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);
        assertThrows(FileNotFoundException.class, () -> PublicKeyReader.get(file));
    }

    @Test
    void get_file() {
        String file = "src/test/resources/rsa/public_key.der";
        assertDoesNotThrow(() -> {
            PublicKey key = PublicKeyReader.get(file);
            assertNotNull(key);
        });
    }
}