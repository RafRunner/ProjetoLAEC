package Factories

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import groovy.transform.CompileStatic

@CompileStatic
class ConfiguracaoGeralFactory {
    
    static ConfiguracaoGeral fromStringMap(Map<String, String> map) {
        String tituloConfiguracao = map.tituloConfiguracao

        List<Classe> classes = (map.classes as List<Map<String, String>>).collect { ClasseFactory.fromStringMap(it) }

        Condicao1 condicao1 = Condicao1Factory.fromStringMap(map.condicao1 as Map<String, String>, classes)
        Condicao2 condicao2 = Condicao2Factory.fromStringMap(map.condicao2 as Map<String, String>, classes)
        LinhaDeBase linhaDeBase = LinhaDeBaseFactory.fromStringMap(map.linhaDeBase as Map<String, String>, classes)
        Teste1 teste1 = Teste1Factory.fromStringMap(map.teste1 as Map<String, String>, classes)
        Teste2 teste2 = Teste2Factory.fromStringMap(map.teste2 as Map<String, String>, classes)

        return new ConfiguracaoGeral(tituloConfiguracao, classes, condicao1, linhaDeBase, condicao2, teste1, teste2)
    }
}
