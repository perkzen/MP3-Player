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
        music.start();
        boolean play = true;

        System.out.println("Commands:\n-play\n-stop\n-quit\n-next\n-prev");

        while (play) {

            String command = sc.next();
            pauseSong(command, music);
            playSong(command, music);
            nextSong(command, playlist, pointer, music, play);
            prevSong(command, playlist, pointer, music, play);
            play = quitMusicPlayer(command, music);

        }

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

    public static boolean quitMusicPlayer(String com, Clip music) {
        if (com.equals("quit")) {
            music.close();
            return false;
        } else {
            return true;
        }
    }

    public static void nextSong(String com, String[] playlist, int pointer, Clip prev, boolean play) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

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

            while (play) {
                Scanner sc = new Scanner(System.in);
                String command = sc.next();
                pauseSong(command, music);
                playSong(command, music);
                nextSong(command, playlist, pointer, music, play);
                prevSong(command, playlist, pointer, music, play);
                play = quitMusicPlayer(command, music);
            }
        }
    }

    public static void prevSong(String com, String[] playlist, int pointer, Clip current, boolean play) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

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

            while (play) {
                Scanner sc = new Scanner(System.in);
                String command = sc.next();
                pauseSong(command, music);
                playSong(command, music);
                nextSong(command, playlist, pointer, music, play);
                prevSong(command, playlist, pointer, music, play);
                play = quitMusicPlayer(command, music);
            }
        }
    }
}
