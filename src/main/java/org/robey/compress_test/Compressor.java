package org.robey.compress_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.DeflaterOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class Compressor {
    private static final int BUFFER_SIZE = 64 * 1024; // 64KB buffer

    public static void apacheCommonsCompress(String sourceFilePath, String destFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destFilePath);
             GzipCompressorOutputStream gzipOs = new GzipCompressorOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFilePath)) {
            final byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                gzipOs.write(buffer, 0, length);
            }
        }
    }

    public static void regularCompress(String sourceFilePath, String destFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destFilePath);
             DeflaterOutputStream zipStream = new DeflaterOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFilePath)) {
            final byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zipStream.write(buffer, 0, length);
            }
        }
    }

    public static void zeroCopyCompress(String sourceFilePath, String destFilePath) throws IOException {
        final Path sourcePath = new File(sourceFilePath).toPath();
        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileOutputStream fos = new FileOutputStream(destFilePath);
             DeflaterOutputStream dos = new DeflaterOutputStream(fos)) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            byte[] temp = new byte[BUFFER_SIZE];

            while (sourceChannel.read(buffer) != -1) {
                buffer.flip();
                int limit = buffer.limit();
                buffer.get(temp, 0, limit);
                dos.write(temp, 0, limit);
                buffer.clear();
            }
            dos.finish();
        }
    }

    public static void strictZeroCopyCompress(String sourceFilePath, String destFilePath) throws IOException {
        final Path sourcePath = new File(sourceFilePath).toPath();
        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileOutputStream fos = new FileOutputStream(destFilePath);
             DeflaterOutputStream dos = new DeflaterOutputStream(fos)) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

            while (sourceChannel.read(buffer) != -1) {
                buffer.flip();
                int limit = buffer.limit();
                dos.write(buffer. array(), 0, limit);
                buffer.clear();
            }
            dos.finish();
        }
    }
}
