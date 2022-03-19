import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

import algorithms.BubbleSort;
import algorithms.InsertionSort;
import algorithms.SelectionSort;
import algorithms.SortAlgorithm;
import algorithms.SortCallback;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import javax.sound.sampled.*;

import javax.swing.*;

public class SortFrame extends JFrame {

    JPanel drawPanel;
    JPanel buttonPanelLeft;
    JPanel statsPanelTop;
    GridBagConstraints c;

    JTextField txtTickInterval;

    JButton btnNextFrame;
    JButton btnPreviousFrame;
    JButton btnQuit;
    JButton btnStartSort;
    JLabel lblTickInterval;
    JLabel lblCurrFrame;
    JSlider sldTickInterval;

    Timer timer;

    //Frame stats
    int maxFrames;
    int currFrame = 0;

    int[] array;

    ArrayList<int[]> steps = new ArrayList<>();
    ArrayList<int[]> highlights = new ArrayList<>();

    public SortFrame(int arraySize, String algo) {


        drawPanel = new JPanel();

        setVisible(true);
        setLayout(new BorderLayout());

        array = generateArray(arraySize);

        /*
         * GraphicsPanel graphicsPanel = new GraphicsPanel(array);
         * add(graphicsPanel, BorderLayout.CENTER);
         */

        int start[] = { 0, 0 };
        drawPanel = new GraphicsPanel(array, start);
        add(drawPanel, BorderLayout.CENTER);
        setVisible(true);

        SortAlgorithm _algo = null;

        if (algo.equals("bubble")) {
            _algo = new BubbleSort();
        }

        if (algo.equals("selection")) {
            _algo = new SelectionSort();
        }

        if (algo.equals("insertion")){
            _algo = new InsertionSort();
        }

        if (_algo != null) {
            _algo.sort(array, new SortCallback(){
                @Override
                public void update(int[] _highlights, int[] _steps) {
                    highlights.add(_highlights);
                    steps.add(_steps);                    
                }
            });
        } else {
            System.err.println(String.format("No or invalid algorithm '%s'", algo));
        }


        buttonPanelLeft = new JPanel();
        this.add(buttonPanelLeft, BorderLayout.WEST);

        buttonPanelLeft.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        // Quit button
        btnQuit = new JButton("end");
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null) {
                    timer.cancel();
                }
                dispose();
            }
        });
        c.gridy = 2;
        buttonPanelLeft.add(btnQuit, c);

        // Next frame button
        btnNextFrame = new JButton("next Frame");
        btnNextFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayNewFrame();
            }
        });
        c.gridwidth = 1;
        buttonPanelLeft.add(btnNextFrame, c);

        // Previous frame button
        btnPreviousFrame = new JButton("prev Frame");
        btnPreviousFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new Error("missing implementation!");
            }
        });
        c.gridy = 1;
        buttonPanelLeft.add(btnPreviousFrame, c);

        // Start button
        btnStartSort = new JButton("Start");
        btnStartSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer delay = Integer.parseInt(txtTickInterval.getText());  
    
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
    
                    @Override
                    public void run() {
                        displayNewFrame();
                        // After finishing all stepst, cancel timer
                        if (currFrame == steps.size()) {
                            this.cancel();
                        }
                    }
    
                }, 500, delay);
            }
        });
        c.gridx = 0;
        c.gridy = 6;
        buttonPanelLeft.add(btnStartSort, c);

        // Tick interval slider + label

        int defaultTick = 500;

        lblTickInterval = new JLabel("Tick interval in ms");
        c.gridy = 3;
        buttonPanelLeft.add(lblTickInterval, c);
        // Textbox to input an interval
        txtTickInterval = new JTextField(5);
        txtTickInterval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ms = Integer.parseInt(((JTextField)e.getSource()).getText());
                setTickInterval(ms);
            }
        });
        c.gridy = 5;
        buttonPanelLeft.add(txtTickInterval, c);
        

        sldTickInterval = new JSlider(10, 2000, defaultTick);
        sldTickInterval.setPaintTicks(true);
        sldTickInterval.setMajorTickSpacing(1);
        sldTickInterval.setPaintLabels(true);

        sldTickInterval.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // On changes update tick interval
                int value = ((JSlider) e.getSource()).getValue();
                setTickInterval(value);
            }
        });

        c.gridy = 4;
        buttonPanelLeft.add(sldTickInterval, c);


        // Current frame
        lblCurrFrame = new JLabel();
        c.gridx = 0;
        c.gridy = 7;
        buttonPanelLeft.add(lblCurrFrame, c);


        statsPanelTop = new JPanel();
        add(statsPanelTop, BorderLayout.NORTH);
        
        setSize(1000, 700);

        setTickInterval(defaultTick);
    }

    private void setTickInterval(Integer ms) {
        txtTickInterval.setText(ms.toString());
        sldTickInterval.setValue(ms);
    }


    public void displayNewFrame() {
        if (currFrame < steps.size()) {
            int[] frame = steps.get(currFrame);
            int[] highlight = highlights.get(currFrame);

            lblCurrFrame.setText(String.format("Frame %d / %d", currFrame+1,steps.size()));

            // playSwitchSound();

            // System.out.println("nextFrame pressed");

            remove(drawPanel);
            drawPanel = new GraphicsPanel(frame, highlight);

            this.add(drawPanel, BorderLayout.CENTER);
            setVisible(true);
            repaint();
            currFrame++;
        }
    }

    private void playSwitchSound() {

        Path p = Path.of("./src/switch.wav");
        File sound = new File(p.toString());

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(String.format("Couldnt find file '%s'", p.toString()));
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    private int[] generateArray(int arraySize) {

        int[] temp = new int[arraySize];

        // fill with numbers
        for (int i = 1; i <= arraySize; i++) {
            temp[i - 1] = i;
        }
        

        // mix numbers
        for (int x = 0; x < arraySize; x++) {

            Random rand = new Random();
            int new_pos = rand.nextInt(arraySize);
            int n = temp[new_pos];
            temp[new_pos] = temp[x];
            temp[x] = n;
        }

        return temp;

    }

}
