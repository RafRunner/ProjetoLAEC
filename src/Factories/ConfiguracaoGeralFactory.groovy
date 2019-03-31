package Factories

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeralFactory {
    
    static ConfiguracaoGeral fromJsonMap(Map<String, String> jsonMap) {
        String tituloConfiguracao = jsonMap.tituloConfiguracao

        Integer tempoLimite = Integer.parseInt(jsonMap.tempoLimite)
        List<Classe> classes = (jsonMap.classes as List<Map<String, String>>).collect { ClasseFactory.fromJsonMap(it) }
        Ordens ordem = Ordens.values().find { Ordens ordem -> ordem.nomeOrdem == jsonMap.ordem }

        Condicao1 condicao1 = Condicao1Factory.fromJsonMap(jsonMap.condicao1 as Map<String, String>, classes)
        LinhaDeBase linhaDeBase = LinhaDeBaseFactory.fromJsonMap(jsonMap.linhaDeBase as Map<String, String>, classes)
        Treino treino = TreinoFactory.fromJsonMap(jsonMap.treino as Map)
        Teste1 teste1 = Teste1Factory.fromJsonMap(jsonMap.teste1 as Map<String, String>, classes)
        Teste2 teste2 = Teste2Factory.fromJsonMap(jsonMap.teste2 as Map<String, String>, classes)

        return new ConfiguracaoGeral(tituloConfiguracao, tempoLimite, classes, ordem, condicao1, linhaDeBase, treino, teste1, teste2)
    }
}
