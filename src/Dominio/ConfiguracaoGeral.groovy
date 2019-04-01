package Dominio

import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Utils.TextUtils
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeral implements Jsonable {

    String tituloConfiguracao

    Integer tempoLimite
    List<Classe> classes
    Ordens ordem

    Condicao1 condicao1
    LinhaDeBase linhaDeBase
    Treino treino
    Teste1 teste1
    Teste2 teste2

    ConfiguracaoGeral(String tituloConfiguracao, Integer tempoLimite, List<Classe> classes, Ordens ordem,
                      Condicao1 condicao1, LinhaDeBase linhaDeBase, Treino treino, Teste1 teste1, Teste2 teste2) {
        this.tituloConfiguracao = tituloConfiguracao
        this.tempoLimite = tempoLimite
        this.classes = classes
        this.ordem = ordem
        this.condicao1 = condicao1
        this.linhaDeBase = linhaDeBase
        this.treino = treino
        this.teste1 = teste1
        this.teste2 = teste2
    }

    List<Instrucao> getTodasAsInstrucoes() {
        List<Instrucao> todasAsInstrucoes = []
        todasAsInstrucoes.addAll(condicao1.instrucoes)
        todasAsInstrucoes.addAll(linhaDeBase.instrucoes)
        todasAsInstrucoes.addAll(teste1.instrucoes)
        todasAsInstrucoes.addAll(teste2.instrucoes)

        return todasAsInstrucoes
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"tituloConfiguracao\": \"${tituloConfiguracao}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\",")

        json.append("\"classes\": ${TextUtils.listToJsonString(classes.collect { it.toJson() })},")

        json.append("\"ordem\": \"${ordem.nomeOrdem}\",")
        json.append("\"ordemFases\": \"${ordem.ordemFases}\",")
        json.append("\"linhaDeBase\": ${linhaDeBase.toJson()},")
        json.append("\"condicao1\": ${condicao1.toJson()},")
        json.append("\"treino\": ${treino.toJson()},")
        json.append("\"teste1\": ${teste1.toJson()},")
        json.append("\"teste2\": ${teste2.toJson()}")
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }

    @Override
    String montaNomeArquivo() {
        return tituloConfiguracao + '.json'
    }
}
