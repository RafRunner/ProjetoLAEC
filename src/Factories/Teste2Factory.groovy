package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Factory {

    static Teste2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        LinhaDeBase linhaDeBase = LinhaDeBaseFactory.fromStringMap(map, classes)
        Instrucao intrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))


        return new Teste2(linhaDeBase, intrucaoImagem, instrucaoPalavra)
    }
}
