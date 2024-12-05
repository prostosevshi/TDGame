package helpz;

import objects.PathPoint;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "save";
    public static String levelFile = "level.txt";
    public static String filepath = homePath + File.separator + saveFolder + File.separator + levelFile;
    private static File lvlFile = new File(filepath);

    public static void createFolder() {
        File folder = new File(homePath + File.separator + saveFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void createLevel(int[] idArr) {

        if (lvlFile.exists()) {
            System.out.println("File " + lvlFile + " already exists");
            return;
        } else {
            try {
                lvlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeToFile(idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    public static BufferedImage getSpriteAtlas() {

        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static AudioInputStream getMusic() {
        AudioInputStream audioStream = null;
        // Access the audio file from the resources folder
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("sickness.wav");

        try {
            // Read the audio input stream
            if (is != null) {
                audioStream = AudioSystem.getAudioInputStream(is);
            } else {
                System.out.println("Music file not found: ");
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return audioStream;
    }

    private static void writeToFile(int[] idArr, PathPoint start, PathPoint end) {
        try {
            PrintWriter pw = new PrintWriter(lvlFile);
            for (Integer i : idArr)
                pw.println(i);
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> readFromFile() {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner sc = new Scanner(lvlFile);

            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveLevel(int[][] idArr, PathPoint start, PathPoint end) {

        if (lvlFile.exists()) {
            writeToFile(Utilz.twoDTo1DIntArr(idArr), start, end);
        } else {
            System.out.println("File " + lvlFile + " does not exist");
            return;
        }
    }

    public static int[][] getLevelData() {

        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile();
            return Utilz.arrayListTo2DInt(list, 20, 20);
        } else {
            System.out.println("File " + lvlFile + " does not exist");
            return null;
        }
    }

    public static ArrayList<PathPoint> getLevelPathPoints() {

        if (lvlFile.exists()) {
            ArrayList<Integer> list = readFromFile();
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File " + lvlFile + " does not exist");
            return null;
        }
    }
}
