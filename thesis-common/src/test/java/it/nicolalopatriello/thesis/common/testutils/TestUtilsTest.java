package it.nicolalopatriello.thesis.common.testutils;

import it.nicolalopatriello.thesis.common.testutils.exceptions.TestRuntimeException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Test Utils")
class TestUtilsTest {

    private static File file;

    @BeforeAll
    static void before() throws IOException {
        assertDoesNotThrow(() -> TestUtils.deleteTestHome(TestUtilsTest.class));
        file = new File("src/test/resources/test-file.txt");
        assertTrue(file.createNewFile());
    }

    @AfterAll
    static void after() {
        assertDoesNotThrow(() -> TestUtils.deleteTestHome(TestUtilsTest.class));
        assertTrue(file.delete());
    }

    @Test
    void text() throws IOException {
        final String textInsideFile = "text inside file test";
        Files.write(Paths.get(file.getPath()), textInsideFile.getBytes());
        String text = TestUtils.text(file);
        assertEquals(textInsideFile.length(), text.length());
    }

    @Test
    void create_home() {
        File testHome = TestUtils.createTestHome(TestUtilsTest.class);
        assertNotNull(testHome);
        assertThrows(NullPointerException.class, () -> TestUtils.createTestHome(null));
    }

    @Test
    void get_test_home() {
        File testHome = TestUtils.getTestHome(TestUtilsTest.class);
        assertNotNull(testHome);
    }

    @Test
    void delete_test_home() {
        File testHome = TestUtils.createTestHome(TestUtilsTest.class);
        assertDoesNotThrow(() -> TestUtils.deleteTestHome(TestUtilsTest.class));
    }

    @Test
    void assert_in_test_home() {
        assertThrows(TestRuntimeException.class,
                () -> TestUtils.assertInTestHome(TestUtilsTest.class, "pppp/ppp.txt"));
        assertDoesNotThrow(() -> TestUtils.assertInTestHome(TestUtilsTest.class, "/tmp/kirk-test/" + TestUtilsTest.class.getSimpleName() + "/ppp.txt"));
    }

    @Test
    void assert_for() {
        long interval = 5;
        int attempts = 2;
        String message = "test_finish";
        BooleanSupplier assertion = mock(BooleanSupplier.class);
        assertThrows(TestRuntimeException.class,
                () -> TestUtils.assertFor(interval, attempts, assertion, message));

        BooleanSupplier assertion1 = mock(BooleanSupplier.class);
        when(assertion1.getAsBoolean()).thenReturn(Boolean.TRUE);
        assertDoesNotThrow(() -> TestUtils.assertFor(assertion1, message));
    }

}