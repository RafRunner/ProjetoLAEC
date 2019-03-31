package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseFactory {

    static LinhaDeBase fromJsonMap(Map<String, String> jsonMap, List<Classe> classes) {
        Instrucao intrucaoImagem = InstrucaoFactory.fromJsonMap((jsonMap.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromJsonMap((jsonMap.instrucaoPalavra as Map<String, String>))
        Integer repeticoes = Integer.parseInt(jsonMap.repeticoes)
        String modoExibicao = jsonMap.modoExibicao

        return new LinhaDeBase(intrucaoImagem, instrucaoPalavra, classes, repeticoes, modoExibicao)
    }
}
