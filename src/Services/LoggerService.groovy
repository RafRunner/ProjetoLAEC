package Services

import FIles.Ambiente
import FIles.Logger
import groovy.transform.CompileStatic

@CompileStatic
class LoggerService {

    private static String pastaResultados = 'Resultados'

    Ambiente ambiente = Ambiente.instancia

    static LoggerService instancia = new LoggerService()

    private LoggerService(){}

    void salvaResultado(Logger logger) {
        String nomeArquivo = logger.montaNomeArquivo() + '.txt'
        String caminhoArquivo = ambiente.getFullPath(pastaResultados, nomeArquivo)
        String resultado = logger.criaResultado()

        File arquivo = new File(caminhoArquivo)
        int numeroArquivo = 1

        while (arquivo.exists()) {
            arquivo = new File(caminhoArquivo - ".txt" + "(${numeroArquivo})" + ".txt")
            numeroArquivo++
        }

        arquivo.write(resultado)
    }
}
