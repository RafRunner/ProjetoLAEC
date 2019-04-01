package Services

import Dominio.ConfiguracaoGeral
import Dominio.Exceptions.EntradaInvalidaException
import Factories.ConfiguracaoGeralFactory
import Files.Ambiente
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeralService {

    private static String pastaConfiguracoes = 'Configuracoes'

    Ambiente ambiente = Ambiente.instancia
    ClasseService classeService = ClasseService.instancia
    InstrucaoService instrucaoService = InstrucaoService.instancia

    static ConfiguracaoGeralService instancia = new ConfiguracaoGeralService()
    private ConfiguracaoGeralService() {}

    ConfiguracaoGeral obtemConfiguracaoDoArquivo(String nomeArquivo) {
        String caminhoCompleto = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)
        File arquivo = new File(caminhoCompleto)

        if (!arquivo.exists()) {
            throw new EntradaInvalidaException('Arquivo de configuracao não existe! Caminho do suposto arquivo: ' + caminhoCompleto)
        }

        String json = arquivo.getText()
        JsonSlurper jsonSlurper = new JsonSlurper()
        Map configuracao = jsonSlurper.parseText(json) as Map

        return ConfiguracaoGeralFactory.fromStringMap(configuracao)
    }

    void salvaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        String nomeArquivo = configuracaoGeral.montaNomeArquivo()
        String json = configuracaoGeral.toJson()

        String caminhoArquivo = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)
        File arquivo = new File(caminhoArquivo)
        arquivo.write(json)

        classeService.salvarClasses(configuracaoGeral.classes)
        instrucaoService.salvarInstrucoes(configuracaoGeral.todasAsInstrucoes)
    }
}
