package Services


import Dominio.Instrucao
import Factories.InstrucaoFactory
import Files.Ambiente
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class InstrucaoService {

    private static String pastaInstrucoes = 'Instrucoes'

    private Ambiente ambiente = Ambiente.instancia

    static InstrucaoService instancia = new InstrucaoService()
    private InstrucaoService() {}

    List<Instrucao> obtenhaTodasAsInstrucoes() {
        List<File> arquivosConfiguracao = ambiente.getFiles(pastaInstrucoes)

        return arquivosConfiguracao?.collect { obtemInstrucaoDoArquivo(it) }?.findAll { it }
    }

    Instrucao obtemInstrucaoDoArquivo(File arquivo) {
        String json = arquivo.getText()

        try {
            JsonSlurper jsonSlurper = new JsonSlurper()
            Map instrucao = jsonSlurper.parseText(json) as Map

            return InstrucaoFactory.fromStringMap(instrucao)

        } catch (Exception ignored) {
            return null
        }
    }

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
