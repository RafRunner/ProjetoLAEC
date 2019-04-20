package Files

import javax.sound.sampled.*

enum EfeitosSonoros {

    ACERTO("acerto.wav"),
    ERRO("erro.wav")

    private Clip clip

    EfeitosSonoros(String soundFileName) {
        URL url = this.getClass().getClassLoader().getResource(soundFileName)
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url)
        clip = AudioSystem.getClip()
        clip.open(audioInputStream)
    }

    void play() {
        if (clip.isRunning()) {
            clip.stop()
        }
        clip.setFramePosition(0)
        clip.start()
    }

    static void init() {
        values()
    }
}

