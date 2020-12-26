package com.domen;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner sc = new Scanner(System.in);
	    File file = new File("levels.wav");
	    AudioInputStream stream = AudioSystem.getAudioInputStream(file);
	    Clip clip = AudioSystem.getClip();
	    clip.open(stream);

	    clip.start();

    }
}
