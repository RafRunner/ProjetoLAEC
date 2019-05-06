package Dominio.Enums

import Controllers.Condicao2Controller
import Controllers.ControllerFase
import Controllers.LinhaDeBaseController
import Controllers.Teste1Controller
import Controllers.Teste2Controller
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import groovy.transform.CompileStatic

@CompileStatic
enum Ordens {

    ORDEM1('Ordem 1',
            'Grupo 1',
            [LinhaDeBase.simpleName, Condicao2.simpleName, Teste1.simpleName, Teste2.simpleName],
            [LinhaDeBaseController, Condicao2Controller, Teste2Controller, Teste1Controller])

    String nomeOrdem
    String nomeGrupo
    ArrayList<String> ordemFases
    ArrayList<Class<? extends ControllerFase>> ordemControllers


    Ordens(String nomeOrdem, String nomeGrupo, List<String> ordemFases, List<Class<? extends ControllerFase>> ordemControllers) {
        this.nomeOrdem = nomeOrdem
        this.nomeGrupo = nomeGrupo
        this.ordemFases = (ArrayList) ordemFases
        this.ordemControllers = (ArrayList) ordemControllers
    }
}