package org.robey.compress_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.System.out;
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;
import static org.robey.compress_test.Compressor.apacheCommonsCompress;
import static org.robey.compress_test.Compressor.regularCompress;
import static org.robey.compress_test.Compressor.strictZeroCopyCompress;
import static org.robey.compress_test.Compressor.zeroCopyCompress;

/**
 * Test GZip file compression using several different algorithms and utilities
 */
public class CompressorTests {
    // 1.3GB file
    static final String FILE_URL = "https://testfile.org/1.3GBiconpng";

    static final String SOURCE_FILE = "test-data/compressor-test.in";
    static final String DEST_FILE = "test-data/compressor-test.out.gz";

    String source = SOURCE_FILE;
    String dest = DEST_FILE;

    @Before
    public void setup() throws Exception {
        File sourceFile = new File(source);

        // TODO: download file but this doesn't work on my work VPN so I download manually :/
        downloadFile(FILE_URL, new File(source));

        out.println("Compressing file: " + source + " to: " + dest + " size: " + sourceFile.length() + " bytes");

        File destFile = new File(dest);
        if(destFile.canRead()) destFile.delete();
    }

    @Test
    public void testStandardCompressAlg() throws Exception {
        String time = timeIt(() -> regularCompress(source, dest));
        out.println("*** Standard Compress Algorithm took: " + time);
    }

    @Test
    public void testApacheCommonsCompressAlg() throws Exception {
        String time = timeIt(() -> apacheCommonsCompress(source, dest));
        out.println("*** Apache Commons Compress Algorithm took: " + time);
    }

    @Test
    public void testModifiedZeroCopyCompress() throws Exception {
        String time = timeIt(() -> zeroCopyCompress(source, dest));
        out.println("*** Zero Copy Compress Algorithm took: " + time);
    }

    @Ignore("throws java.lang.UnsupportedOperationException when trying to write to direct byte buffer")
    @Test
    public void testStrictZeroCopyCompressionAlg() throws Exception {
        String time = timeIt(() -> strictZeroCopyCompress(source, dest));
        out.println("*** Strict Zero Copy Compress Algorithm took: " + time);
    }

    @FunctionalInterface interface RunnableThatThrows {
        void run() throws Exception;
    }
    static String timeIt(RunnableThatThrows operationToTime) throws Exception {
        final StopWatch watch = StopWatch.createStarted();
        operationToTime.run();
        watch.stop();
        return watch.formatTime();
    }
    void downloadFile(String sourceURL, File outFile) throws Exception {
        if(outFile.exists()) return;
        out.println("Downloading file to: " + outFile.getName());
        try(final InputStream is = new URL(sourceURL).openStream()) {
            final String contents = new String(is.readAllBytes());
            try(final FileOutputStream fos = new FileOutputStream(outFile)) {
                fos.write(contents.getBytes());
            }
        }
    }
}
