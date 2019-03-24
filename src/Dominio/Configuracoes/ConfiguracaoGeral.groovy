package Dominio.Configuracoes

import Dominio.Classe
import Dominio.Enums.Ordens
import Dominio.Enums.Sexo
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Dominio.Jsonable
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeral implements Jsonable {

    String tituloConfiguracao

    //Essas informações tem a ver com um experimento, não com configuração

    Integer tempoLimite
    List<Classe> classes
    Integer repeticoes
    Ordens ordem

    Condicao1 condicao1
    LinhaDeBase linhaDeBase
    Treino treino
    Teste1 teste1
    Teste2 teste2

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
}
