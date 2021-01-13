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

public class Main {

    public static boolean isSongPlaying = true;
    private static Clip music;
    private static int pointer = 0;
    private static long timer;

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        File dir = new File("./songs");
        File[] playlist = dir.listFiles();

        AudioInputStream stream = AudioSystem.getAudioInputStream(playlist[pointer]);
        music = AudioSystem.getClip();
        music.open(stream);

        createGUI(playlist);

    }

    public static void playSong() {
        music.start();
        timer = System.currentTimeMillis();
    }

    public static void pauseSong() {
        music.stop();
    }


    public static void nextSong(File[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        music.close();

        if (pointer < playlist.length - 1) {
            pointer++;
        } else {
            pointer = 0;
        }

        AudioInputStream stream = AudioSystem.getAudioInputStream(playlist[pointer]);
        music = AudioSystem.getClip();
        music.open(stream);
        playSong();

        addEndOfSongListener(playlist, songTitle);

        songTitle.setText(playlist[pointer].getName().substring(0, playlist[pointer].getName().indexOf(".wav")));

    }

    public static void prevSong(File[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        music.close();

        if (pointer > 0) {
            pointer--;
        } else {
            pointer = playlist.length - 1;
        }

        AudioInputStream stream = AudioSystem.getAudioInputStream(playlist[pointer]);
        music = AudioSystem.getClip();
        music.open(stream);
        playSong();

        addEndOfSongListener(playlist, songTitle);

        songTitle.setText(playlist[pointer].getName().substring(0, playlist[pointer].getName().indexOf(".wav")));
    }

    public static void restartSong(File[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        music.close();

        AudioInputStream stream = AudioSystem.getAudioInputStream(playlist[pointer]);
        music = AudioSystem.getClip();
        music.open(stream);
        playSong();

        addEndOfSongListener(playlist, songTitle);
    }

    public static void randomSong(File[] playlist, JLabel songTitle) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        Random r = new Random();

        music.close();
        pointer = r.nextInt(playlist.length);

        AudioInputStream stream = AudioSystem.getAudioInputStream(playlist[pointer]);
        music = AudioSystem.getClip();
        music.open(stream);
        playSong();

        addEndOfSongListener(playlist, songTitle);

        songTitle.setText(playlist[pointer].getName().substring(0, playlist[pointer].getName().indexOf(".wav")));
    }

    public static void addEndOfSongListener(File[] playlist, JLabel songTitle) {
        music.addLineListener(new LineListener() {
            @Override
            public void update(final LineEvent event) {
                if (event.getType().equals(LineEvent.Type.STOP)) {
                    if (isSongPlaying && System.currentTimeMillis() - timer > 10000) {
                        try {
                            nextSong(playlist, songTitle);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static void createGUI(File[] playlist) {
        JFrame f = new JFrame("Music player");
        f.setMinimumSize(new Dimension(700, 500));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel();

        JLabel songTitle = new JLabel(playlist[pointer].getName().substring(0, playlist[pointer].getName().indexOf(".wav")));
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

        playSong();
        addEndOfSongListener(playlist, songTitle);

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
                timer = System.currentTimeMillis();

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
                timer = System.currentTimeMillis();

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
                timer = System.currentTimeMillis();

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
                timer = System.currentTimeMillis();

                try {
                    restartSong(playlist, songTitle);
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
