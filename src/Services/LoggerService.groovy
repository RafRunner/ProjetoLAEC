package Services

import Dominio.Exceptions.EntradaInvalidaException
import Files.Ambiente
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class LoggerService {

    private static String pastaResultados = 'Resultados'

    Ambiente ambiente = Ambiente.instancia

    static LoggerService instancia = new LoggerService()
    private LoggerService(){}

    void atualizaNomeArquivoCasoJaExista(Logger logger) {
        String caminhoArquivo = montaCaminhoArquivo(logger)

        File arquivo = new File(caminhoArquivo)
        int numeroArquivo = 1

        while (arquivo.exists()) {
            arquivo = new File(caminhoArquivo - ".txt" + "(${numeroArquivo})" + ".txt")
            numeroArquivo++
        }

        logger.nomeArquivo = arquivo.name
    }

    void criarArquivoResultado(Logger logger) {
        String caminhoArquivo = montaCaminhoArquivo(logger)
        String resultado = logger.criaArquivoResultado()

        File arquivo = new File(caminhoArquivo)

        arquivo.write(resultado)
    }

    void registraLog(Logger logger) {
        String caminhoArquivo = montaCaminhoArquivo(logger)
        String logARegistrar = logger.log

        File arquivo = new File(caminhoArquivo)
        try {
            arquivo.append(logARegistrar)
        } catch (IOException ignored) {
            throw new EntradaInvalidaException("Se tentou registrar no arquivo de log sem o ter criado primeiro!")
        }

        logger.limpaLog()
    }

    private String montaCaminhoArquivo(Logger logger) {
        return ambiente.getFullPath(pastaResultados, logger.nomeArquivo)
    }
}
