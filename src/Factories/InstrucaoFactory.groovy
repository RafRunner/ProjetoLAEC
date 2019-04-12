package Factories


import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class InstrucaoFactory {

    static Instrucao fromStringMap(Map<String, String> map) {
        String texto = map.texto

        return new Instrucao(texto)
    }
}
