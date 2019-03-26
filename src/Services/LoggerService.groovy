package Services

import Files.Ambiente
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class LoggerService {

    private static String pastaResultados = 'Resultados'

    Ambiente ambiente = Ambiente.instancia

    static LoggerService instancia = new LoggerService()
    private LoggerService(){}

    void criarArquivoResultado(Logger logger) {
        String caminhoArquivo = criaCaminhoArquivo(logger.nomeArquivo)
        String resultado = logger.criaArquivoResultado()

        File arquivo = new File(caminhoArquivo)
        int numeroArquivo = 1

        while (arquivo.exists()) {
            arquivo = new File(caminhoArquivo - ".txt" + "(${numeroArquivo})" + ".txt")
            numeroArquivo++
        }

        logger.nomeArquivo = arquivo.name
        arquivo.write(resultado)
    }

    void registraLog(Logger logger) {
        String caminhoArquivo = criaCaminhoArquivo(logger.nomeArquivo)
        String logARegistrar = logger.log

        File arquivo = new File(caminhoArquivo)
        try {
            arquivo.append(logARegistrar)
        } catch (IOException ignored) {
            throw new IOException("Se tentou registrar no arquivo de log sem o ter criado primeiro!")
        }

        logger.limpaLog()
    }

    private String criaCaminhoArquivo(String nomeArquivo) {
        return ambiente.getFullPath(pastaResultados, nomeArquivo)
    }
}
