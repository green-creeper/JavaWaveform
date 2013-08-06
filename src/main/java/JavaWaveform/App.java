package JavaWaveform;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class App 
{
    public static void main( String[] args ) throws UnsupportedAudioFileException, IOException {
        File file = new File(args[0]);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        int frameLength = (int) audioInputStream.getFrameLength();
        int frameSize = (int) audioInputStream.getFormat().getFrameSize();
        int numChannels = audioInputStream.getFormat().getChannels();
        int[] toReturn = new int[frameLength];
        byte[] bytes = new byte[frameLength * frameSize];

        int result = 0;
        try {
            result = audioInputStream.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int sampleIndex = 0;

        for (int t = 0; t < bytes.length;) {
                int low = (int) bytes[t];
                t++;
                int high = (int) bytes[t];
                t++;
                int sample = getSixteenBitSample(high, low);
                toReturn[sampleIndex] = sample;
            sampleIndex++;
        }

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("data.txt"), "utf-8"));
            writer.write("[");
            for (int i = 0; i < toReturn.length; i+=(toReturn.length/1500)) {
                writer.write(Double.valueOf(toReturn[i])/4000 + ",");
            }
            writer.write("]");
        } catch (IOException ex){
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }
    private static int getSixteenBitSample(int high, int low) {
        return (high << 8) + (low & 0x00ff);
    }
}
