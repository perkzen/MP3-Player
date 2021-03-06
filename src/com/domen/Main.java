package com.domen;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static boolean isSongPlaying = true;
    private static Clip music;
    private static int pointer = 0;

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        Scanner sc = new Scanner(System.in);
        String[] playlist = {"Koosen - Mood (Remix).wav", "Avicii - levels.wav", "Day 'N' Nite (Crookers Remix).wav", "HVME - GOOSEBUMPS (Official Video).wav", "Vinne - Pursuit of Happiness (feat. NorthStarAndre).wav"};
        System.out.println("Commands:\n-play\n-stop\n-quit\n-next\n-prev\n-random");
        System.out.println();

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        music = AudioSystem.getClip();
        music.open(stream);

        createGUI(playlist);

        music.start();
    }

    public static void playSong() {
        music.start();
    }

    public static void pauseSong() {
        music.stop();
    }


    public static void nextSong(String[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        music.close();

        if (pointer < playlist.length - 1) {
            pointer++;
        } else {
            pointer = 0;
        }

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        music = AudioSystem.getClip();
        music.open(stream);
        music.start();

        songTitle.setText(playlist[pointer].substring(0, playlist[pointer].indexOf(".wav")));

    }

    public static void prevSong(String[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        music.close();

        if (pointer > 0) {
            pointer--;
        } else {
            pointer = playlist.length - 1;
        }

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        music = AudioSystem.getClip();
        music.open(stream);
        music.start();

        songTitle.setText(playlist[pointer].substring(0, playlist[pointer].indexOf(".wav")));
    }

    public static void restartSong(String[] playlist) throws IOException, LineUnavailableException, UnsupportedAudioFileException{
        music.close();

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        music = AudioSystem.getClip();
        music.open(stream);
        music.start();
    }

    public static void randomSong(String[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        Random r = new Random();

        music.close();
        pointer = r.nextInt(playlist.length);

        File file = new File(playlist[pointer]);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        music = AudioSystem.getClip();
        music.open(stream);
        music.start();

        songTitle.setText(playlist[pointer].substring(0, playlist[pointer].indexOf(".wav")));
    }


    public static void createGUI(String[] playlist) {
        JFrame f = new JFrame("Music player");
        f.setMinimumSize(new Dimension(700, 500));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel();

        JLabel songTitle = new JLabel(playlist[pointer].substring(0, playlist[pointer].indexOf(".wav")));
        songTitle.setBorder(new EmptyBorder(f.getHeight() / 3, 0, 0, 0));
        songTitle.setFont(new Font(songTitle.getFont().getName(), Font.PLAIN, songTitle.getFont().getSize() * 2));

        main.add(songTitle, BorderLayout.CENTER);

        JPanel navigation = new JPanel();
        navigation.setBackground(new java.awt.Color(20, 54, 66));
        navigation.setBorder(new EmptyBorder(0, 500, 0, 0));

        JButton action = new JButton("pause");
        action.setBorder(new EmptyBorder(20, 15, 20, 15));

        JButton prev = new JButton("prev");
        prev.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton next = new JButton("next");
        next.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton random = new JButton("random");
        next.setBorder(new EmptyBorder(10, 15, 10, 15));

        JButton restart = new JButton("restart");
        next.setBorder(new EmptyBorder(10, 15, 10, 15));


        navigation.setBorder(new EmptyBorder(10, 10, 10, 10));

        navigation.add(restart);
        navigation.add(prev);
        navigation.add(action);
        navigation.add(next);
        navigation.add(random);


        f.add(main, BorderLayout.CENTER);
        f.add(navigation, BorderLayout.PAGE_END);
        f.setVisible(true);


        action.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isSongPlaying = !isSongPlaying;
                if (isSongPlaying) {
                    playSong();
                    action.setText("pause");
                } else {
                    pauseSong();
                    action.setText("play");
                }

            }
        });
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.setText("pause");
                isSongPlaying = true;


                try {
                    prevSong(playlist, songTitle);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }

            }
        });
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.setText("pause");
                isSongPlaying = true;

                try {
                    nextSong(playlist, songTitle);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
            }
        });
        random.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.setText("pause");
                isSongPlaying = true;

                try {
                    randomSong(playlist, songTitle);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
            }
        });
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.setText("pause");
                isSongPlaying = true;

                try {
                    restartSong(playlist);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
            }
        });
    }
}

