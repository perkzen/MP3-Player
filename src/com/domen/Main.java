package com.domen;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
        music.start();

        System.out.println("Commands:\n-play\n-stop\n-quit\n-next\n-prev\n-random");
        mainLoop(music,playlist,pointer);
    }

    public static void playSong(String com, Clip music) {
        if (com.equals("play")) {
            music.start();
        }
    }

    public static void pauseSong(String com, Clip music) {
        if (com.equals("pause")) {
            music.stop();
        }
    }

    public static void quitMusicPlayer(String com) {
        if (com.equals("quit")) {
            System.exit(0);
        }
    }

    public static void nextSong(String com, String[] playlist, int pointer, Clip prev) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        if (com.equals("next")) {
            prev.close();

            if (pointer < playlist.length - 1) {
                pointer++;
            } else {
                pointer = 0;
            }

            File file = new File(playlist[pointer]);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip music = AudioSystem.getClip();
            music.open(stream);
            music.start();

            mainLoop(music,playlist,pointer);
        }
    }

    public static void prevSong(String com, String[] playlist, int pointer, Clip current) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        if (com.equals("prev")) {
            current.close();

            if (pointer > 0) {
                pointer--;
            } else {
                pointer = playlist.length - 1;
            }

            File file = new File(playlist[pointer]);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip music = AudioSystem.getClip();
            music.open(stream);
            music.start();

            mainLoop(music,playlist,pointer);
        }
    }

    public static void randomSong(String com, String[] playlist, int pointer, Clip prev) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        Random r = new Random();

        if (com.equals("random")) {
            prev.close();
            pointer = r.nextInt(playlist.length);

            File file = new File(playlist[pointer]);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip music = AudioSystem.getClip();
            music.open(stream);
            music.start();

            mainLoop(music,playlist,pointer);
        }
    }

    public static void mainLoop(Clip music, String[]playlist, int pointer) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String command = sc.next();
            pauseSong(command, music);
            playSong(command, music);
            nextSong(command, playlist, pointer, music);
            prevSong(command, playlist, pointer, music);
            randomSong(command, playlist, pointer, music);
            quitMusicPlayer(command);
        }
    }
}

