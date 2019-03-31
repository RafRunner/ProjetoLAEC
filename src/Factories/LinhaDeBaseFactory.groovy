package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseFactory {

    static LinhaDeBase fromStringMap(Map<String, String> map, List<Classe> classes) {
        Instrucao intrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))
        Integer repeticoes = Integer.parseInt(map.repeticoes)
        String modoExibicao = map.modoExibicao

        return new LinhaDeBase(intrucaoImagem, instrucaoPalavra, classes, repeticoes, modoExibicao)
    }
}
