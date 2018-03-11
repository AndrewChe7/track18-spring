package ru.track.io;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.track.io.vendor.Bootstrapper;
import ru.track.io.vendor.FileEncoder;
import ru.track.io.vendor.ReferenceTaskImplementation;

import java.io.*;
import java.net.InetSocketAddress;

public final class TaskImplementation implements FileEncoder {

    /**
     * @param finPath  where to read binary data from
     * @param foutPath where to write encoded data. if null, please create and use temporary file.
     * @return file to read encoded data from
     * @throws IOException is case of input/output errors
     */
    @NotNull
    public File encodeFile(@NotNull String finPath, @Nullable String foutPath) throws IOException {
        /* XXX: https://docs.oracle.com/javase/8/docs/api/java/io/File.html#deleteOnExit-- */
        File inputFile = new File(finPath);
        File outputFile;

        if (foutPath == null) {
            outputFile = File.createTempFile("base64", ".txt");
            outputFile.deleteOnExit();
        } else {
            outputFile = new File(foutPath);
        }

        InputStream inputStream = new FileInputStream(inputFile);
        FileWriter fileWriter = new FileWriter(outputFile);

        byte[] inputBytes = new byte[3];

        int readLen = inputStream.read(inputBytes, 0, 3);

        while (readLen != -1) {
            int fullInput = 0;
            for (int i = 0; i < readLen; i++) {
                fullInput += inputBytes[i] << ((2 - i) * 8);
            }
            for (int i = 0; i < readLen + 1; i++) {
                int index = (fullInput >> (3 - i) * 6) % 64;
                fileWriter.write(toBase64[index]);
            }
            if (readLen == 1) fileWriter.write("==");
            if (readLen == 2) fileWriter.write("=");
        }
        return outputFile;
    }

    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    public static void main(String[] args) throws Exception {
        final FileEncoder encoder = new TaskImplementation();
        // NOTE: open http://localhost:9000/ in your web browser
        (new Bootstrapper(args, encoder))
                .bootstrap("", new InetSocketAddress("127.0.0.1", 9000));
    }

}
