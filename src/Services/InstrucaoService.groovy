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
        List<File> instrucoesExistentes = ambiente.getFiles(pastaInstrucoes)

        for (Instrucao instrucao : instrucoes) {
            if (!instrucao) {
                continue
            }

            String nomeArquivo = instrucao.montaNomeArquivo()

            if (!(nomeArquivo in instrucoesExistentes.name)) {
                String caminhoArquivo = ambiente.getFullPath(pastaInstrucoes, nomeArquivo)
                File arquivo = new File(caminhoArquivo)
                arquivo.write(instrucao.toJson())
            }
        }
    }
}
