package ar.edu.unahur.obj2.impostoresPaises

import Pais
import Observatorio
import io.kotest.assertions.throwables.shouldThrowMessage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class PaisTest: DescribeSpec({
    Observatorio.paises.clear()
    describe("Test de paises") {

        val ejemplotopia = Pais(
            "Ejemplotopia",
            "EXT",
            "EXS",
            "Antártida",
            0.77,
            873456,
            2345.8,
            mutableListOf(),
            listOf("ATC","UNASUR"),
            mutableListOf("Inglés")
        )
        val argentina = Pais(
            "Argentina",
            "ARG",
            "ARS",
            "América",
            130.0,
            2467,
            687.8,
            mutableListOf(),
            listOf("UNASUR"),
            mutableListOf("Español")
        )
        val chile = Pais(
            "Chile",
            "CHL",
            "CLP",
            "América",
            902.0,
            87653,
            5378.8,
            mutableListOf(),
            listOf("UNASUR"),
            mutableListOf("Español", "Quechua")
        )

        val brasil = Pais(
            "Brasil",
            "BRA",
            "REA",
            "América",
            50.10,
            523791,
            7544.3,
            mutableListOf(),
            listOf("UNASUR"),
            mutableListOf("Portugués")
        )
        val bolivia = Pais(
            "Bolivia",
            "BOL",
            "BLV",
            "América",
            6.8,
            53985,
            9427.7,
            mutableListOf(),
            listOf("UNASUR"),
            mutableListOf("Castellano", "Quechua", "Aymara")
        )
        val paraguay = Pais(
            "Paraguay",
            "PAR",
            "PRA",
            "América",
            33.5,
            27843,
            8659.4,
            mutableListOf(),
            listOf("UNASUR"),
            mutableListOf("Castellano", "Español")
        )
        val portugal = Pais(
            "Portugal",
            "POR",
            "EUR",
            "Europa",
            0.33,
            379,
            385.2,
            mutableListOf(),
            listOf("EUROPA"),
            mutableListOf("Portugués")
        )
        val francia = Pais(
            "Francia",
            "FRA",
            "EUR",
            "Europa",
            0.33,
            7242,
            8735.6,
            mutableListOf(),
            listOf("EUROPA"),
            mutableListOf("Francés")
        )
        val espania = Pais(
            "España",
            "ESP",
            "EUR",
            "Europa",
            0.33,
            2478,
            6583.1,
            mutableListOf(),
            listOf("EUROPA"),
            mutableListOf("Español")
        )
        val islandia = Pais(
            "Islandia",
            "ISL",
            "ILS",
            "América",
            5.72,
            139,
            2758.2,
            mutableListOf(),
            listOf("UNANOR"),
            mutableListOf("Noruego")
        )

        argentina.paisesLimitrofes.add(chile)
        chile.paisesLimitrofes.add(argentina)
        brasil.paisesLimitrofes.add(paraguay)
        brasil.paisesLimitrofes.add(argentina)
        paraguay.paisesLimitrofes.add(argentina)
        paraguay.paisesLimitrofes.add(brasil)
        argentina.paisesLimitrofes.add(paraguay)
        argentina.paisesLimitrofes.add(brasil)
        bolivia.paisesLimitrofes.add(chile)
        bolivia.paisesLimitrofes.add(argentina)
        bolivia.paisesLimitrofes.add(brasil)
        chile.paisesLimitrofes.add(bolivia)
        francia.paisesLimitrofes.add(espania)
        portugal.paisesLimitrofes.add(espania)
        espania.paisesLimitrofes.add(portugal)
        espania.paisesLimitrofes.add(francia)

        describe("test etapa 1: funciones de paises") {

            it("ejemplotopia es plurinacional"){ejemplotopia.esPlurinacional().shouldBeFalse()}
            it("argentina no es plurinacional"){argentina.esPlurinacional().shouldBeFalse()}
            it("chile es plurinacional"){chile.esPlurinacional().shouldBeTrue()}
            it("ejemplotopia es insular"){ejemplotopia.esIsla().shouldBeTrue()}
            it("argentina no es insular"){argentina.esIsla().shouldBeFalse()}
            it("chile no es insular"){chile.esIsla().shouldBeFalse()}
            it("densidad poblacional de ejemplotopia (372)"){ejemplotopia.densidadPoblacional().shouldBe(372)}
            it("densidad poblacional de argentina (4)"){argentina.densidadPoblacional().shouldBe(4)}
            it("densidad poblacional de chile (16)"){chile.densidadPoblacional().shouldBe(16)}
            it("vecino mas poblado de ejemplotopia... no tiene"){ejemplotopia.vecinoMasPoblado().shouldBe(ejemplotopia)}
            it("vecino mas poblado de argentina"){argentina.vecinoMasPoblado().shouldBe(brasil)}
            it("vecino mas poblado de chile"){chile.vecinoMasPoblado().shouldBe(chile)}
        }

        describe("test etapa 2: observatorio") {

            Observatorio.paises.add(ejemplotopia)
            Observatorio.paises.add(argentina)
            Observatorio.paises.add(chile)

            it("deberia tirar error de identificacion") {
                shouldThrowMessage("El pais arjentina no está registrado") {
                    Observatorio.sonLimitrofes("Chile", "arjentina")
                }
            }

            it("ejemplotopia y argentina NO son limítrofes"){
                Observatorio.sonLimitrofes("Ejemplotopia", "Argentina").shouldBeFalse()
            }
            it("chile y argentina SI son limítrofes") {
                Observatorio.sonLimitrofes("Chile", "Argentina").shouldBeTrue()
            }

            it("ejemplotopia y argentina necesitan traduccion"){
                Observatorio.necesitanTraduccion("Ejemplotopia", "Argentina").shouldBeTrue()
            }
            it("chile y argentina NO necesitan traduccion"){
                Observatorio.necesitanTraduccion("Chile", "Argentina").shouldBeFalse()
            }

            it("ejemplotopia y chile NO son potenciales aliados"){
                Observatorio.sonPotencialesAliados("Ejemplotopia", "Chile")
            }
            it("argentina y chile SI son potenciales aliados"){
                Observatorio.sonPotencialesAliados("Argentina", "Chile")
            }

            it("de ejemplotopia a chile conviene ir"){
                Observatorio.sePuedeComprarDe_En_("Ejemplotopia", "Chile").shouldBeTrue()
            }
            it("...pero de chile a argentina, no"){
                Observatorio.sePuedeComprarDe_En_("Chile", "Argentina").shouldBeFalse()
            }

            it("cotizacion de chile a argentina (1 -> 0.14)"){
                Observatorio.cotizarMonedaDe_A_("Chile", "Argentina", 1.0).shouldBe(0.14 plusOrMinus 0.01)
            }
            it("cotizacion de ejemplotopia a argentina (10 -> 1688.31)"){
                Observatorio.cotizarMonedaDe_A_( "Ejemplotopia", "Argentina", 10.0).shouldBe(1688.31 plusOrMinus 0.01)
            }

            Observatorio.paises.add(brasil)
            Observatorio.paises.add(bolivia)
            Observatorio.paises.add(paraguay)
            Observatorio.paises.add(espania)
            Observatorio.paises.add(portugal)
            Observatorio.paises.add(francia)
            Observatorio.paises.add(islandia)

            it("los 5 paises (ISO) con mayor densidad poblacional (EXT, BRA, CHL, BOL, ARG)"){
                Observatorio.cincoPaisesMayorDensidadP().shouldContainExactly("EXT", "BRA", "CHL", "BOL", "ARG")
            }
            it("que continente tiene mas paises plurinacionales (america)"){
                Observatorio.continenteMasPlurinacional().shouldBe("América")
            }
            it("promedio de densidad poblacional de los paises que son islas (186)"){
                Observatorio.promedioDensidadIslas().shouldBe(186)
            }
        }
    }
})
