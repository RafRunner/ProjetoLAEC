package Dominio.Enums

import groovy.transform.CompileStatic

@CompileStatic
enum Sexo {

    FEMININO('F', 'feminino'),
    MASCULINO('M', 'masculino'),
    OUTRO('O', 'outro')

    String abreviacao
    String extenso

    Sexo(String abreviacao, String extenso) {
        this.abreviacao = abreviacao
        this.extenso = extenso
    }
}