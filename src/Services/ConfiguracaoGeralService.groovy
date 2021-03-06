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

    private Ambiente ambiente = Ambiente.instancia
    private ClasseService classeService = ClasseService.instancia
    private InstrucaoService instrucaoService = InstrucaoService.instancia

    static ConfiguracaoGeralService instancia = new ConfiguracaoGeralService()

    private ConfiguracaoGeralService() {}

    List<ConfiguracaoGeral> obtemTodasAsConfiguracoes() {
        List<File> arquivosConfiguracao = ambiente.getFiles(pastaConfiguracoes)

        return arquivosConfiguracao?.collect { obtemConfiguracaoDoArquivo(it) }?.findAll { it }
    }

    ConfiguracaoGeral obtemConfiguracaoDoArquivo(File arquivo) {
        String json = arquivo.getText()

        try {
            JsonSlurper jsonSlurper = new JsonSlurper()
            Map configuracao = jsonSlurper.parseText(json) as Map

            return ConfiguracaoGeralFactory.fromStringMap(configuracao)

        } catch (Exception ignored) {
            return null
        }
    }

    ConfiguracaoGeral obtemConfiguracaoDoArquivo(String nomeArquivo) {
        String caminhoCompleto = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)
        File arquivo = new File(caminhoCompleto)
        if (!arquivo.exists()) {
            throw new EntradaInvalidaException('Arquivo de configuracao não existe! Caminho do suposto arquivo: ' + caminhoCompleto)
        }

        return obtemConfiguracaoDoArquivo(arquivo)
    }

    boolean existeConfiguracao(String titulo) {
        List<File> arquivosConf = ambiente.getFiles(pastaConfiguracoes)
        return titulo + '.json' in arquivosConf.name
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
