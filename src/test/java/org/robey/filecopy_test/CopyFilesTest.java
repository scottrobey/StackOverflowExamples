package org.robey.filecopy_test;

import org.junit.Before;
import org.junit.Test;

import static org.robey.utils.Download.download;

public class CopyFilesTest {
    // 1.3GB file
    //static final String FILE_URL = "https://testfile.org/1.3GBiconpng";

    // 4.4gb file
    static final String FILE_URL = "https://releases.ubuntu.com/jammy/ubuntu-22.04.5-desktop-amd64.iso";

    // assumes cwd is project root
    static final String SOURCE = "test-data/CopyFilesTest.input";
    static final String DEST = "test-data/CopyFilesTest.output";

    @Test
    public void testCopies() throws Exception {
        // [Tue Mar 11 16:45:45 CDT 2025] [Test worker] Took: 1175 to copy file
        LargeFileCopy.copyUsingNIO2Streams(SOURCE, DEST);

        //[Tue Mar 11 16:55:19 CDT 2025] [Test worker] Took: 1029 to copy file using direct ByteBuffer
        LargeFileCopy.copyUsingDirectByteBuffer(SOURCE, DEST);

        // [Wed Mar 26 19:01:26 CDT 2025] [Test worker] (Legacy copy) Took: 13752 to copy file
        LargeFileCopy.copyUsingLegacyJavaIO(SOURCE, DEST);

        // [Wed Mar 26 19:06:51 CDT 2025] [Test worker] (Native copy) Took: 739 to copy file using direct ByteBuffer
        LargeFileCopy.copyUsingNativeCommand(SOURCE, DEST);
    }

    @Before
    public void stageFile() throws Exception {
        download(FILE_URL, SOURCE);
    }
}
