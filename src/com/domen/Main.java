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

	    String command = "";
		System.out.println("Commands:\n-play\n-stop\n-quit");
		while (!command.equals("quit")) {
			command = sc.next();
			command = command.toLowerCase();
			switch (command) {
				case "play":
					clip.start();
					break;
				case "stop":
					clip.stop();
					break;
				case "quit":
					clip.close();
					break;
				default:
					System.out.println("This command doesn't exist");
					break;
			}
		}
    }
}
