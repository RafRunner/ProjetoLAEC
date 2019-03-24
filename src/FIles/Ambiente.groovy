package FIles

class Ambiente {

    static Ambiente instancia = new Ambiente()

    String rootDirectory = System.getProperty('user.dir')
    String sistemaOperacional = System.getProperty('os.name').toLowerCase()

    private Ambiente() {
    }

    String getFullPath(String nomePasta, String nomeArquivo = null) {
        String fullPath
        String separadorEndereco

        if (isLinux() || isOsx()) {
            separadorEndereco = '/'
        }
        else if (isWindows()) {
            separadorEndereco = '//'
        }
        else {
            throw new Exception('Sistema operacional não suportado!')
        }

        fullPath = rootDirectory + separadorEndereco + nomePasta
        if (nomeArquivo) {
            fullPath += separadorEndereco + nomeArquivo
        }
        return fullPath
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
