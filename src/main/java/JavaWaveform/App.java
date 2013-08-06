package JavaWaveform;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage \n JavaWaveform.jar [filename]");
            return;
        }
        File file = new File(args[0]);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (IOException | UnsupportedAudioFileException e) {
            System.out.println("couldn't open file");
            e.printStackTrace();
        }
        int frameLength = (int) audioInputStream.getFrameLength();
        int frameSize = audioInputStream.getFormat().getFrameSize();
        int[] toReturn = new int[frameLength];
        byte[] bytes = new byte[frameLength * frameSize];

        int result = 0;
        try {
            result = audioInputStream.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int sampleIndex = 0;

        for (int t = 0; t < bytes.length; ) {
            int low = (int) bytes[t];
            t++;
            int high = (int) bytes[t];
            t++;
            int sample = getSixteenBitSample(high, low);
            toReturn[sampleIndex] = sample;
            sampleIndex++;
        }

        new Exporter(toReturn);
    }

    private static int getSixteenBitSample(int high, int low) {
        return (high << 8) + (low & 0x00ff);
    }
}
