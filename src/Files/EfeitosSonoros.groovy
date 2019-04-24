package Files

import javax.sound.sampled.*

enum EfeitosSonoros {

    ACERTO("acerto.wav"),
    ERRO("erro.wav")

    private Clip clip

    private Ambiente ambiente = Ambiente.instancia
    private String pastaAudio = 'Audio'

    EfeitosSonoros(String soundFileName) {
        InputStream audio = new FileInputStream(new File(ambiente.getFullPath(pastaAudio, soundFileName)))
        clip = AudioSystem.getClip()
        BufferedInputStream bufferStream = new BufferedInputStream(audio)
        clip.open(AudioSystem.getAudioInputStream(bufferStream))
    }

    void play() {
        if (clip.isRunning()) {
            clip.stop()
        }
        clip.setFramePosition(0)
        clip.start()
    }
}
