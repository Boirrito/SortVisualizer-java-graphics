package sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat.Encoding;

public class AudioPlayer {

    private final Path _filePath = Paths.get("").resolve("assets/sounds/switch.wav");
    private Clip audioClip = null;
    private AudioInputStream audioStream;
    
    private void open(String path) {
        File sound = new File(path);
        try {
            setAudioStream(AudioSystem.getAudioInputStream(sound));
            setAudioClip(AudioSystem.getClip());
            getAudioClip().open(getAudioStream());
        } catch (UnsupportedAudioFileException e) {
            System.err.println(String.format("Unsupported file format '%s'", path));
        }
        catch (LineUnavailableException e) {
            System.err.println(String.format("Line unavailable '%s'", path));
        }
        catch (IOException e) {
            System.err.println(String.format("Couldnt find file '%s'", path));
        } catch (Exception e) {
            System.err.println(String.format("Unknown exception occured '%s'", path));
            e.printStackTrace();
        }
    }

    public AudioPlayer() {
        
        open(getFilePath());

    }

    private void setAudioStream(AudioInputStream as) {
        this.audioStream = as;
    }

    private AudioInputStream getAudioStream() {
        return this.audioStream;
    }

    private void setAudioClip(Clip ac) {
        this.audioClip = ac;
    }

    private Clip getAudioClip() {
        return this.audioClip;
    }

    private String getFilePath() {
        return _filePath.toAbsolutePath().toString();
    }

    /**
     * Thanks to https://www.technetexperts.com/web/change-the-pitch-of-audio-using-java-sound-api/
     * @param inFormat
     * @param pitch
     * @return
     */
    private AudioFormat pitchSound(AudioFormat inFormat, float pitch) {

        // Prevent sample rate of 0
        if (pitch == 0) {
            pitch = .001f;
        }

        // Offset pitch by one
        // pitch+=.5;

        int channels = inFormat.getChannels();
        float sampleRate = inFormat.getSampleRate();  
        Encoding encoding = inFormat.getEncoding();
        int sampleSize = inFormat.getSampleSizeInBits();
        // Multiply by 2?
        int frameSize = inFormat.getFrameSize();
        float frameRate = inFormat.getFrameRate();
        // int frameSize = Math.round(inFormat.getFrameSize() * (pitch+1));

        // Pitch is applied by changing the sample rate
        // float newSampleRate = (sampleRate / 100) * (pitch * 100);
        float newSampleRate = sampleRate * pitch;
        
        System.out.println(String.format("New sample new rate: %f rate %f pitch %f size %d frameRate %f", newSampleRate, sampleRate, pitch, sampleSize, frameRate));
        // Fallback to original format
        AudioFormat ret = getAudioStream().getFormat();
        try {
            ret = new AudioFormat(encoding, newSampleRate, sampleSize, channels, channels*2, frameRate, inFormat.isBigEndian());
        } catch (Exception e) {
            // System.err.println("");
            e.printStackTrace();
        }

        return ret;
    }

    public void play() {
        play(1f);
    }

    public void play(float pitch) {
        System.out.println(String.format("Sound! %f", pitch));

        String path = getFilePath();

        File sound = new File(path);
        try {
            getAudioClip().stop();
            getAudioClip().setMicrosecondPosition(0);
            setAudioStream(AudioSystem.getAudioInputStream(sound));
            AudioFormat pitched = pitchSound(getAudioStream().getFormat(), pitch);
            AudioInputStream pitchedStream = AudioSystem.getAudioInputStream(pitched, getAudioStream());
            setAudioClip(AudioSystem.getClip());
            getAudioClip().open(pitchedStream);
            getAudioClip().start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println(String.format("Unsupported file format '%s'", path));
        }
        catch (LineUnavailableException e) {
            System.err.println(String.format("Line unavailable '%s'", path));
        }
        catch (IOException e) {
            System.err.println(String.format("Couldnt find file '%s'", path));
        } catch (Exception e) {
            System.err.println(String.format("Unknown exception occured '%s'", path));
            e.printStackTrace();
        }

        // try {
            // getAudioClip().stop();
            // getAudioClip().setMicrosecondPosition(0);
            // getAudioClip().close();
            // AudioFormat pitched = pitchSound(getAudioStream().getFormat(), pitch);
            // AudioInputStream pitchedStream = AudioSystem.getAudioInputStream(pitched, getAudioStream());
            // setAudioClip(AudioSystem.getClip());
            // getAudioClip().open(getAudioStream());
            // getAudioClip().start();
        // } catch (LineUnavailableException | IOException e) {
        //     e.printStackTrace();
        // }
    }
}
