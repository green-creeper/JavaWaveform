package JavaWaveform;

import java.io.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: andrey
 * Date: 06.08.13
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
public class Exporter {
    int[] audioData;
    public static final String TEMPLATE_FILE="wf.html";
    //Control points count
    public static final int PRECISION=1500;
    //Highest peak in audio file
    public static final int NORMALIZATION_FACTOR = 4000;

    public Exporter(int[] audioData) {
        this.audioData = audioData;
        String templateString = null;
        try {
            templateString = new Scanner(new File(TEMPLATE_FILE)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < audioData.length; i += (audioData.length / PRECISION)) {
            sb.append(Double.valueOf(audioData[i]) / NORMALIZATION_FACTOR + ",");
        }
        sb.append("]");

        String output = templateString.replace("<REPLACE>", sb.toString());

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("output.html"), "utf-8"));
            writer.write(output);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }
    }
}
