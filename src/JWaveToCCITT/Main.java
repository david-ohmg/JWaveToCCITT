package JWaveToCCITT;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        if (args.length != 2) {
            System.err.println("Please provide a source and destination path");
            System.exit(1);
        }
        String filePath = args[0];
        String fileDest = filePath + args[1];

        try {
            ConvertFile convert = new ConvertFile(filePath, fileDest);
            for (String f : convert.getFileList()) {
                System.out.println(f);
            }

            boolean result = convert.convertFiles();
            System.out.println("Result of process: " + result);
        }
        catch (UnsupportedAudioFileException | IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }
}