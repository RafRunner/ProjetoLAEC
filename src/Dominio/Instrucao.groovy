package Dominio

import Dominio.Exceptions.EntradaInvalidaException
import groovy.transform.CompileStatic

@CompileStatic
class Instrucao {

    String texto
    int tempo
    boolean podeSerPulada

    private static int tempoMinimo = 0 //s
    private static int tempoMaximo = 120 //s

    Instrucao(String texto, int tempo, Boolean podeSerPulada) {
        if(texto && tempo && podeSerPulada != null) {

            if (tempo < tempoMinimo || tempo > tempoMaximo) {
                throw new EntradaInvalidaException("Tempo fora dos limítes! mínimo: ${tempoMinimo}s máximo: ${tempoMaximo}")
            }
            this.texto = texto
            this.tempo = tempo
            this.podeSerPulada = podeSerPulada

        } else {
            throw new EntradaInvalidaException("Informações incompletas para instrução!")
        }
    }
}
