package Dominio.Fases

import Dominio.Classe
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 extends LinhaDeBase {

    Teste2(Instrucao instrucaoImagem, Instrucao instrucaoPalavra, List<Classe> classes, int repeticoes, String nomeModo) {
        super(instrucaoImagem, instrucaoPalavra, classes, repeticoes, nomeModo)
    }
}
