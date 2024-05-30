package JWaveToCCITT;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConvertFile {
    String filePath;
    String fileDest;
    ConvertFile(String filePath, String fileDest) {
        this.filePath = filePath;
        this.fileDest = fileDest;
    }

    public ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();
        File dir = new File(this.filePath);
        File[] files = dir.listFiles((dir1, name) -> name.contains("wav") || name.contains("mp3"));
        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
        return fileList;
    }

    public boolean convertFiles() throws UnsupportedAudioFileException, IOException {
        ArrayList<String> files = getFileList();
        try {
            for (String file : files) {
                File inputFile = new File(this.filePath + file);
                File outputFile = new File(this.fileDest + file);

                AudioInputStream wavStream = AudioSystem.getAudioInputStream(inputFile);
                AudioFormat wavFormat = wavStream.getFormat();
                AudioFormat uLawFormat = new AudioFormat(
                        AudioFormat.Encoding.ULAW,
                        8000,
                        8,
                        1,
                        1,
                        wavFormat.getSampleRate(),
                        false
                );
                AudioInputStream uLawStream = AudioSystem.getAudioInputStream(uLawFormat, wavStream);
                AudioSystem.write(uLawStream, AudioFileFormat.Type.WAVE, outputFile);
            }
        } catch (IOException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
            e.getStackTrace();
            return false;
        }
        return true;
    }
}
