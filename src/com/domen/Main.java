package com.domen;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner sc = new Scanner(System.in);
        int pointer = 0;
        String[] playlist = {"Koosen - Mood (Remix).wav", "Avicii - levels.wav", "Day 'N' Nite (Crookers Remix).wav", "HVME - GOOSEBUMPS (Official Video).wav", "Vinne - Pursuit of Happiness (feat. NorthStarAndre).wav"};

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        Clip music = AudioSystem.getClip();
        music.open(stream);

        boolean play = true;

        System.out.println("Commands:\n-play\n-stop\n-quit");
        music.start();
        while (play) {
            String command = sc.next();
            pauseSong(command, music);
            unpausetSong(command,music);
            play = quitMusicPlayer(command, music);
        }

    }

    public static void unpausetSong(String com, Clip music) {
        if (com.equals("play")) {
            music.start();
        }
    }

    public static void pauseSong(String com, Clip music) {
        if (com.equals("pause")) {
            music.stop();
        }
    }

    public static boolean quitMusicPlayer(String com, Clip music) {
        if (com.equals("quit")) {
            music.close();
            return false;
        } else {
            return true;
        }
    }
    public static void nextSong(String com, int pointer, Clip prev) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (com.equals("next")) {

        }
    }
}
