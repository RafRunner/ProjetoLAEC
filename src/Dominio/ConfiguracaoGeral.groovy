package Dominio

import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeral implements Jsonable {

    String tituloConfiguracao

    Integer tempoLimite
    List<Classe> classes
    Integer repeticoes
    Ordens ordem

    Condicao1 condicao1
    LinhaDeBase linhaDeBase
    Treino treino
    Teste1 teste1
    Teste2 teste2

    ConfiguracaoGeral(String tituloConfiguracao, Integer tempoLimite, List<Classe> classes, Integer repeticoes, Ordens ordem,
                      Condicao1 condicao1, LinhaDeBase linhaDeBase, Treino treino, Teste1 teste1, Teste2 teste2) {
        this.tituloConfiguracao = tituloConfiguracao
        this.tempoLimite = tempoLimite
        this.classes = classes
        this.repeticoes = repeticoes
        this.ordem = ordem
        this.condicao1 = condicao1
        this.linhaDeBase = linhaDeBase
        this.treino = treino
        this.teste1 = teste1
        this.teste2 = teste2
    }

    ConfiguracaoGeral(Map jsonMap) {
        String tituloConfiguracao = jsonMap.tituloConfiguracao.toString()

        Integer tempoLimite = Integer.parseInt(jsonMap.tempoLimite.toString())
        List<Classe> classes = (jsonMap.classes as List<Map>).collect { Map mapaClasse -> new Classe(mapaClasse) }
        Integer repeticoes = Integer.parseInt(jsonMap.repeticoes.toString())
        Ordens ordem = Ordens.values().find { Ordens ordem -> ordem.nomeOrdem == jsonMap.ordem }

//        Condicao1 condicao1 = (jsonMap.condicao1 as Map).collect { Map mapaCondicao1 -> new Condicao1(mapaCondicao1) }
//        LinhaDeBase linhaDeBase = (jsonMap.linhaDeBase as Map).collect { Map mapaLinhaDeBase -> new Condicao1(mapaLinhaDeBase) }
//        Treino treino = (jsonMap.treino as Map).collect { Map mapaTreino -> new Condicao1(mapaTreino) }
//        Teste1 teste1 = (jsonMap.teste1 as Map).collect { Map mapaTeste1 -> new Condicao1(mapaTeste1) }
//        Teste2 teste2 = (jsonMap.teste2 as Map).collect { Map mapaTeste2 -> new Condicao1(mapaTeste2) }
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"tituloConfiguracao\": \"${tituloConfiguracao}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\",")
        
        json.append("\"classes\": [")
        for (int i = 0; i < classes.size(); i++) {
            Classe classe = classes.get(i)
            if (i == classes.size() - 1) {
                json.append(classe.toJson())
            } else {
                json.append(classe.toJson() + ',')
            }
        }
        json.append('],')
        
        json.append("\"repeticoes\": \"${repeticoes}\",")
        json.append("\"ordem\": \"${ordem.nomeOrdem}\",")
        json.append("\"ordemFases\": \"${ordem.ordemFases}\",")
        json.append("\"linhaDeBase\": { ${linhaDeBase.toJson()} },")
        json.append("\"condicao1\": { ${condicao1.toJson()} },")
        json.append("\"treino\": { ${treino.toJson()} },")
        json.append("\"teste1\": { ${teste1.toJson()} },")
        json.append("\"teste2\": { ${teste2.toJson()} }")
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }

    @Override
    String montaNomeArquivo() {
        return tituloConfiguracao + '.json'
    }
}
