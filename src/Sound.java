import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class Sound
{
	Clip clip = null;
	public void playmusic(String musicfile, boolean repeat) {
		File soundFile = new File(musicfile);
		try {
	//		Clip clip = AudioSystem.getClip();
			if(musicfile.equals("stop")){
				clip.stop();
				clip.flush();
				clip.close();
			}
			else {
				AudioInputStream inputStream= AudioSystem.getAudioInputStream(soundFile);
				if (clip == null || !clip.isOpen()){
					clip = AudioSystem.getClip();
				}
				clip.open(inputStream);
                if (repeat){
                    clip.loop(clip.LOOP_CONTINUOUSLY);
                }
				clip.start();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}