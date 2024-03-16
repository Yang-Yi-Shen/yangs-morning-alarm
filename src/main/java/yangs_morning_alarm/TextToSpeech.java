package yangs_morning_alarm;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import javax.sound.sampled.AudioFileFormat;

public class TextToSpeech {
    // CREDIT: STACKOVERFLOW
    VoiceManager freeVM;
    Voice voice;

    public TextToSpeech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer("morning-alarm", AudioFileFormat.Type.WAVE);
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(165);// Setting the rate of the voice
                voice.setPitch(90);// Setting the Pitch of the voice
                voice.setVolume(3);// Setting the volume of the voice

                // save tts sound output to a .wav file
                voice.setAudioPlayer(audioPlayer);
                voice.allocate();
                voice.speak(words);
                voice.deallocate();

                audioPlayer.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void SpeakText(String words) {
        voice.speak(words);
    }
}
