package Dominio

import Dominio.Enums.Ordens
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import Utils.TextUtils
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeral implements Jsonable {

    String tituloConfiguracao
    List<Classe> classes

    Condicao1 condicao1
    LinhaDeBase linhaDeBase
    Condicao2 condicao2
    Teste2 teste2
    Teste1 teste1

    Instrucao instrucaoInicial
    Instrucao instrucaoFinal

    Ordens ordem

    ConfiguracaoGeral() {}

    ConfiguracaoGeral(String tituloConfiguracao, List<Classe> classes, Condicao1 condicao1, LinhaDeBase linhaDeBase, Condicao2 condicao2, Teste1 teste1, Teste2 teste2) {
        this.tituloConfiguracao = tituloConfiguracao
        this.classes = classes
        this.condicao1 = condicao1
        this.linhaDeBase = linhaDeBase
        this.condicao2 = condicao2
        this.teste1 = teste1
        this.teste2 = teste2
    }

    void setTituloConfiguracao(String titulo) {
        if (!titulo) {
            throw new EntradaInvalidaException('Configuração deve ter um título!')
        }
        this.tituloConfiguracao = titulo
    }

    void setClasses(List<Classe> classes) {
        if (!classes) {
            throw new EntradaInvalidaException('Configuração deve ter pelo menos uma classe!')
        }
        this.classes = classes
    }

    List<Instrucao> getTodasAsInstrucoes() {
        List<Instrucao> todasAsInstrucoes = []
        if (condicao1) {
            todasAsInstrucoes.addAll(condicao1.instrucoes)
        }
        if (linhaDeBase) {
            todasAsInstrucoes.addAll(linhaDeBase.instrucoes)
        }
        if (condicao2) {
            todasAsInstrucoes.addAll(condicao2.instrucoes)
        }
        if (teste2) {
            todasAsInstrucoes.addAll(teste2.instrucoes)
        }
        if (teste1) {
            todasAsInstrucoes.addAll(teste1.instrucoes)
        }

        return todasAsInstrucoes
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"tituloConfiguracao\": \"${tituloConfiguracao}\",")
        json.append("\"ordem\": \"${ordem.nomeOrdem}\",")

        json.append("\"classes\": ${TextUtils.listToJsonString(classes.collect { it.toJson() })},")

        if (instrucaoInicial) {
            json.append("\"instrucaoInicial\": ${instrucaoInicial.toJson()},")
        }
        if (instrucaoFinal) {
            json.append("\"instrucaoFinal\": ${instrucaoFinal.toJson()},")
        }
        if (condicao1) {
            json.append("\"condicao1\": ${condicao1.toJson()},")
        }
        if (condicao2) {
            json.append("\"condicao2\": ${condicao2.toJson()},")
        }
        if (linhaDeBase) {
            json.append("\"linhaDeBase\": ${linhaDeBase.toJson()},")
        }
        if (teste1) {
            json.append("\"teste1\": ${teste1.toJson()},")
        }
        if (teste2) {
            json.append("\"teste2\": ${teste2.toJson()}")
        }
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }

    @Override
    String montaNomeArquivo() {
        return tituloConfiguracao + '.json'
    }
}
