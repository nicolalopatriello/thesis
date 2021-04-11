package it.nicolalopatriello.thesis.common.testutils;

import it.nicolalopatriello.thesis.common.utils.TimeUtils;
import it.nicolalopatriello.thesis.common.testutils.exceptions.TestRuntimeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.BooleanSupplier;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    public static final File KIRK_TEST_HOME = new File("/tmp/kirk-test"); //NOSONAR
    public static final File XLS_SOURCE_PATH = new File("/files/process.execution");
    public static final File RESOURCE_PATH = new File("src/test/resources");
    public static final File SIMPLE_REPORT_CODE = new File(RESOURCE_PATH + "/engines/groovy/report/simple-test.groovy");
    public static final File EXAMPLE_CSV = new File(RESOURCE_PATH + "/files/example.xls");
    public static final File FILE_TEST = new File(RESOURCE_PATH + "/files/xls/xls-test-table-kirk.xls");
    public static final File FILE_TEST_WARNING = new File(RESOURCE_PATH + "/warnings/test-warning.xls");
    public static final File FILE_EXAMPLE_WARNING_JSON = new File(RESOURCE_PATH + "/warnings/example-warnings.json");
    public static final File FILE_TEST_VERTICAL_ALIGN_JUSTIFY = new File(RESOURCE_PATH + "/files/xls/xls-vertical-justify.xls");

    static {
        try {
            setUpFolderResourcesForTest();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void setUpFolderResourcesForTest() throws IOException {
        FileUtils.copyDirectory(RESOURCE_PATH, KIRK_TEST_HOME);
        System.setProperty("input.dir", KIRK_TEST_HOME.getAbsolutePath());
        System.setProperty("logs.task.dir", KIRK_TEST_HOME + "/data/logs/task");
    }

    public static void tearDownFolderResourcesForTest() throws IOException {
        final String absolutePath = TestUtils.KIRK_TEST_HOME.getAbsolutePath();
        FileUtils.cleanDirectory(new File(absolutePath));
    }

    public static File createTestHome(Class<?> testClass) {
        File f = getTestHome(testClass);
        if (!f.exists() && !f.mkdirs())
            throw new TestRuntimeException("Cannot create " + f.getAbsolutePath());
        return f;
    }

    public static void deleteTestHome(Class<?> testClass) throws IOException {
        File f = getTestHome(testClass);
        if (f.exists())
            FileUtils.deleteDirectory(f);
    }

    public static void assertInTestHome(Class<?> testClass, String path) {
        assertInTestHome(testClass, new File(path));
    }

    public static void assertInTestHome(Class<?> testClass, File path) {
        File testHome = getTestHome(testClass);
        if (!path.getAbsolutePath().startsWith(testHome.getAbsolutePath()))
            throw new TestRuntimeException("Test Path should be located in " + testHome.getAbsolutePath() + " [" + path.getAbsolutePath() + "]");
    }

    public static void assertFor(BooleanSupplier consumer, String message) {
        assertFor(100L, 20, consumer, message);
    }

    public static void assertFor(long interval, int attempts, BooleanSupplier assertion, String message) {
        for (int i = 0; i < attempts; i++) {
            if (Boolean.TRUE.equals(assertion.getAsBoolean()))
                return;
            TimeUtils.awaitFor(interval);
        }
        throw new TestRuntimeException(message);
    }

    public static File getTestHome(Class<?> testClass) {
        return new File(KIRK_TEST_HOME, testClass.getSimpleName());
    }


    public static String text(File f) throws IOException {
        return FileUtils.readFileToString(f, StandardCharsets.UTF_8);
    }

}

