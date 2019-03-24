package Dominio.Enums

import groovy.transform.CompileStatic

import java.awt.Color

@CompileStatic
enum CoresDisponiveis {

    AZUL('azul', Color.BLUE),
    VERMELHO('vermelho', Color.RED),
    VERDE('verde', Color.GREEN)

    String nomeCor
    Color color

    CoresDisponiveis(String nomeCor, Color cor) {
        this.nomeCor = nomeCor
        this.color = cor
    }
}