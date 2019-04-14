package Dominio.Fases

import Dominio.Classe
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1 extends LinhaDeBase {

    Teste1(Instrucao instrucaoImagem, Instrucao instrucaoPalavra, List<Classe> classes, int repeticoes, String nomeModo, int tempoLimite) {
        super(instrucaoImagem, instrucaoPalavra, classes, repeticoes, nomeModo, tempoLimite)
    }
}
