package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Factory {

    static Teste2 fromJsonMap(Map<String, String> jsonMap, List<Classe> classes) {
        LinhaDeBase linhaDeBase = LinhaDeBaseFactory.fromJsonMap(jsonMap, classes)
        Instrucao intrucaoImagem = InstrucaoFactory.fromJsonMap((jsonMap.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromJsonMap((jsonMap.instrucaoPalavra as Map<String, String>))


        return new Teste2(linhaDeBase, intrucaoImagem, instrucaoPalavra)
    }
}
