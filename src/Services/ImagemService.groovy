package Services

import Files.Ambiente

class ImagemService {

    private static final String nomePastaImgens = 'Imagens'
    private Ambiente ambiente = Ambiente.instancia

    static ImagemService instancia =  new ImagemService()

    private ImagemService() {}

    List<String> getNomesArquivosImagem() {
        List<File> arquivosImagem = ambiente.getFiles(nomePastaImgens)
        return arquivosImagem.name
    }
}
