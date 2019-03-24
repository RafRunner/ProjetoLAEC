package Services

import Dominio.Configuracoes.ConfiguracaoGeral
import Dominio.Exceptions.EntradaInvalidaException
import FIles.Ambiente
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeralService {

    static ConfiguracaoGeralService instancia = new ConfiguracaoGeralService()

    private static String pastaConfiguracoes = 'Configuracoes'

    Ambiente ambiente = Ambiente.instancia

    private ConfiguracaoGeralService() {}

    ConfiguracaoGeral obtemConfiguracaoDoArquivo(String nomeArquivo) {
        String caminhoCompleto = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo) + '.json'

        try {
            File arquivo = new File(caminhoCompleto)
            String json = arquivo.getText()

            JsonSlurper jsonSlurper = new JsonSlurper()
            Map configuracao = jsonSlurper.parseText(json) as Map

        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Arquivo de configuracao não exist ou está na formatação errada!')
        }

        return null
    }

    void salvaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        String nomeArquivo = configuracaoGeral.tituloConfiguracao + '.json'

        String json = configuracaoGeral.toJson()

        String caminhoArquivo = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)
        File arquivo = new File(caminhoArquivo)
        arquivo.write(json)
    }
}
