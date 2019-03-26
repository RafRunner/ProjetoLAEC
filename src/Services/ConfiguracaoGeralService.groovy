package Services

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Exceptions.EntradaInvalidaException
import Files.Ambiente
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeralService {

    private static String pastaConfiguracoes = 'Configuracoes'

    Ambiente ambiente = Ambiente.instancia
    ClasseService classeService = ClasseService.instancia

    static ConfiguracaoGeralService instancia = new ConfiguracaoGeralService()
    private ConfiguracaoGeralService() {}

    ConfiguracaoGeral obtemConfiguracaoDoArquivo(String nomeArquivo) {
        String caminhoCompleto = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)

        try {
            File arquivo = new File(caminhoCompleto)
            String json = arquivo.getText()

            JsonSlurper jsonSlurper = new JsonSlurper()
            Map configuracao = jsonSlurper.parseText(json) as Map
            return new ConfiguracaoGeral(configuracao)

        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Arquivo de configuracao não exist ou está na formatação errada!')
        }
    }

    void salvaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        String nomeArquivo = configuracaoGeral.montaNomeArquivo()
        String json = configuracaoGeral.toJson()

        String caminhoArquivo = ambiente.getFullPath(pastaConfiguracoes, nomeArquivo)
        File arquivo = new File(caminhoArquivo)
        arquivo.write(json)

        List<Classe> classesConfiguracao = configuracaoGeral.classes
        classeService.salvarClasses(classesConfiguracao)
    }
}
