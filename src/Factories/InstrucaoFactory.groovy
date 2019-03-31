package Factories


import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class InstrucaoFactory {

    static Instrucao fromStringMap(Map<String, String> map) {
        String texto = map.texto
        Integer tempo = Integer.parseInt(map.tempo)
        Boolean podeSerPulada = Boolean.valueOf(map.podeSerPulada)

        return new Instrucao(texto, tempo, podeSerPulada)
    }
}
