package Files

import Dominio.Exceptions.EntradaInvalidaException

class Ambiente {

    static Ambiente instancia = new Ambiente()

    private String rootDirectory
    private String sistemaOperacional
    private String separadorEndereco

    private Ambiente() {
        boolean producao = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath().contains('.jar')
        if (producao) {
            URL url = getClass().getProtectionDomain().getCodeSource().getLocation()
            File file = new File(url.toURI())
            rootDirectory = file.getParent()
        } else {
            rootDirectory = System.getProperty('user.dir')
        }
        sistemaOperacional = System.getProperty('os.name')
        separadorEndereco = System.getProperty('file.separator')
    }

    String getFullPath(String nomePasta, String nomeArquivo = null) {
        String fullPath

        fullPath = rootDirectory + separadorEndereco + nomePasta
        if (nomeArquivo) {
            fullPath += separadorEndereco + nomeArquivo
        }
        return fullPath
    }

    List<File> getFiles(String nomePasta) {
        File pasta
        try {
            pasta = new File(getFullPath(nomePasta))
        } catch(Exception ignored) {
            throw new EntradaInvalidaException('Essa pasta não existe!')
        }

        return pasta.listFiles().findAll { File arquivo -> arquivo.isFile() }
    }

}
