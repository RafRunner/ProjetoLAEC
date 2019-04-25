package Files

import Dominio.Exceptions.EntradaInvalidaException

class Ambiente {

    static Ambiente instancia = new Ambiente()

    private String rootDirectory
    private String sistemaOperacional
    private String separadorEndereco

    private Ambiente() {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation()
        File file = new File(url.toURI())
        rootDirectory = file.getParent()
        sistemaOperacional = System.getProperty('os.name').toLowerCase()

        if (isLinux() || isOsx()) {
            separadorEndereco = '/'
        }
        else if (isWindows()) {
            separadorEndereco = '\\'
        }
        else {
            throw new Exception('Sistema operacional não suportado!')
        }
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

    boolean isLinux() {
        return (sistemaOperacional.contains("nix") || sistemaOperacional.contains("aix") || sistemaOperacional.contains("nux"))
    }

    boolean isWindows() {
        return sistemaOperacional.contains('win')
    }

    boolean isOsx() {
        return sistemaOperacional.contains('osx')
    }
}
