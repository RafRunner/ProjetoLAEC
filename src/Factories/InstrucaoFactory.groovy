package Factories


import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class InstrucaoFactory {

    static Instrucao fromJsonMap(Map<String, String> jsonMap) {
        String texto = jsonMap.texto
        Integer tempo = Integer.parseInt(jsonMap.tempo)
        Boolean podeSerPulada = Boolean.valueOf(jsonMap.podeSerPulada)

        return new Instrucao(texto, tempo, podeSerPulada)
    }
}
