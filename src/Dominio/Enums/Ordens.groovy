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

    ORDEM1('Primeiro Teste 1',
            [LinhaDeBase.simpleName, Condicao2.simpleName, Teste1.simpleName, Teste2.simpleName],
            [LinhaDeBaseController, Condicao2Controller, Teste1Controller, Teste2Controller]),

    ORDEM2('Primeiro Teste 2',
           [LinhaDeBase.simpleName, Condicao2.simpleName, Teste2.simpleName, Teste1.simpleName],
           [LinhaDeBaseController, Condicao2Controller, Teste2Controller, Teste1Controller])

    String nomeOrdem
    ArrayList<String> ordemFases
    ArrayList<Class<? extends ControllerFase>> ordemControllers


    Ordens(String nomeOrdem, List<String> ordemFases, List<Class<? extends ControllerFase>> ordemControllers) {
        this.nomeOrdem = nomeOrdem
        this.ordemFases = (ArrayList) ordemFases
        this.ordemControllers = (ArrayList) ordemControllers
    }
}