package Services


import Dominio.Instrucao
import Files.Ambiente
import groovy.transform.CompileStatic

@CompileStatic
class InstrucaoService {

    private static String pastaInstrucoes = 'Instrucoes'

    private Ambiente ambiente = Ambiente.instancia

    static InstrucaoService instancia = new InstrucaoService()
    private InstrucaoService() {}

    void salvarInstrucoes(List<Instrucao> instrucoes) {
        for (Instrucao instrucao : instrucoes) {

            if (!instrucao) {
                continue
            }

            String nomeArquivo = instrucao.montaNomeArquivo()

            if (!instrucaoJaExiste(nomeArquivo)) {
                String caminhoArquivo = ambiente.getFullPath(pastaInstrucoes, nomeArquivo)
                File arquivo = new File(caminhoArquivo)
                arquivo.write(instrucao.toJson())
            }
        }
    }

    private  boolean instrucaoJaExiste(String nomeArquivoInstrucao) {
        return ambiente.getFiles(pastaInstrucoes).find { File arquivo -> arquivo.name == nomeArquivoInstrucao }
    }
}
