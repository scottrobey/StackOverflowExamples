package org.robey.compress_test;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.System.out;
import static org.robey.compress_test.Compressor.apacheCommonsCompress;
import static org.robey.compress_test.Compressor.regularCompress;
import static org.robey.compress_test.Compressor.strictZeroCopyCompress;
import static org.robey.compress_test.Compressor.zeroCopyCompress;
import static org.robey.utils.Download.download;
import static org.robey.utils.Log.log;
import static org.robey.utils.Time.timeIt;

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
        download(FILE_URL, source);

        log("Compressing file: " + source + " to: " + dest + " size: " + sourceFile.length() + " bytes");

        File destFile = new File(dest);
        if(destFile.canRead()) destFile.delete();
    }

    @Test
    public void testStandardCompressAlg() throws Exception {
        String time = timeIt(() -> regularCompress(source, dest));
        result("Standard Compress Algorithm took: " + time);
    }

    @Test
    public void testApacheCommonsCompressAlg() throws Exception {
        String time = timeIt(() -> apacheCommonsCompress(source, dest));
        result("Apache Commons Compress Algorithm took: " + time);
    }

    @Test
    public void testModifiedZeroCopyCompress() throws Exception {
        String time = timeIt(() -> zeroCopyCompress(source, dest));
        result("Zero Copy Compress Algorithm took: " + time);
    }

    @Ignore("throws java.lang.UnsupportedOperationException when trying to write to direct byte buffer")
    @Test
    public void testStrictZeroCopyCompressionAlg() throws Exception {
        String time = timeIt(() -> strictZeroCopyCompress(source, dest));
        result("Strict Zero Copy Compress Algorithm took: " + time);
    }

    void result(String msg) {
        out.println("*** {CompressorTest Result} " + msg);
    }

}
