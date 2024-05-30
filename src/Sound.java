import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class Sound
{
	Clip clip = null;
	public void playMusic(String musicfile, boolean repeat) {
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
			//System.out.println(e);
		}
	}

	// public void stopmusic(){
	// 	clip.stop();
	// 	clip.flush();
	// 	clip.close();
	// }
	public void playmusic(String musicfile, boolean repeat) {
		File soundFile = new File(musicfile);
		try {
			if(musicfile.equals("stop")){
				if (clip != null && clip.isRunning()) {
					clip.stop();
					clip.flush();
					clip.close();
				}
			}
			else {
				try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile)) {
					if (clip != null && clip.isRunning()){
						clip.stop();
						clip.flush();
						clip.close();
					}
					clip = AudioSystem.getClip();
					clip.open(inputStream);
					//volume doesnt adjust
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-10.0f);
					if (repeat){
						clip.loop(clip.LOOP_CONTINUOUSLY);
					}
					clip.start();
				}
			}
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}

    public void stopmusic(){
        clip.stop();
        clip.flush();
        clip.close();
    }
}