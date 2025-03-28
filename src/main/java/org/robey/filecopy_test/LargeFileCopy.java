package org.robey.filecopy_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.robey.utils.Log.log;
import static org.robey.utils.Time.timeIt;

public class LargeFileCopy {
    private static final int BUFFER_SIZE = 64 * 1024; // 64KB buffer

    public static void copyUsingLegacyJavaIO(String source, String dest) throws Exception {
        checkSourceAndDest(source, dest);

        String time = timeIt( () -> {
                                  try (FileInputStream input = new FileInputStream(source);
                                       FileOutputStream output = new FileOutputStream(dest)) {
                                      byte[] buf = new byte[1024];
                                      int bytesRead;
                                      while ((bytesRead = input.read(buf)) > 0) {
                                          output.write(buf, 0, bytesRead);
                                      }
                                  }
                              });

        log("(Legacy copy) Took: " + time);
    }

    public static void copyUsingNIO2Streams(String sourceFile, String destFile) throws Exception {
        File dest = checkSourceAndDest(sourceFile, destFile);

        String time = timeIt( () -> Files.copy(new File(sourceFile).toPath(), dest.toPath()) );

        log("(NIO2 Files copy) Took: " + time);
    }

    public static void copyUsingDirectByteBuffer(String sourceFile, String destFile) throws Exception {
        final Path sourcePath = new File(sourceFile).toPath();
        File dest = checkSourceAndDest(sourceFile, destFile);

        String time = timeIt( () -> {
                                  try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
                                       FileOutputStream fos = new FileOutputStream(dest)) {

                                      ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
                                      byte[] temp = new byte[BUFFER_SIZE];

                                      while (sourceChannel.read(buffer) != -1) {
                                          buffer.flip();
                                          int limit = buffer.limit();
                                          buffer.get(temp, 0, limit);
                                          fos.write(temp, 0, limit);
                                          buffer.clear();
                                      }
                                      fos.flush();
                                  }
                              });

        log("(Direct ByteBuffer copy) Took: " + time);
    }

    public static void copyUsingNativeCommand(String sourceFile, String destFile) throws Exception {
        checkSourceAndDest(sourceFile, destFile);

        String time = timeIt( () -> {
                                  // works in Linux/unix only if 'cp' command works
                                  String command = "cp " + sourceFile + " " + destFile;
                                  Process process = Runtime.getRuntime().exec(command);
                                  int exitCode = process.waitFor();

                                  if (exitCode != 0) throw new RuntimeException("cp command failed with exit code: " + exitCode);
                              });

        log("(Native copy/cp command) Took: " + time);
    }

    private static File checkSourceAndDest(String source, String dest) {
        File sourceFile = new File(source);
        if(!sourceFile.canRead()) throw new IllegalArgumentException("Can't read source file: " + source);
        File destFile = new File(dest);
        if(destFile.canRead()) destFile.delete();
        return destFile;
    }
}
