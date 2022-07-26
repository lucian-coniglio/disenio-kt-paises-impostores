import kotlin.math.roundToInt

class Pais (
  val nombre: String,
  val codigoISO3: String,
  val codigoMonedaLocal: String,
  val continente: String,
  val cotizacionDolar: Double?,
  val poblacion: Long,
  val superficie: Double,
  var paisesLimitrofes: List<Pais>?,
  var bloquesRegionales: List<String>?,
  var idiomas: List<String>?)
{
  init {
    paisesLimitrofes = paisesLimitrofes.orEmpty()
    bloquesRegionales = bloquesRegionales.orEmpty()
    idiomas = idiomas.orEmpty()
  }

  fun esPlurinacional(): Boolean = return idiomas!!.size > 1

  fun cantIdiomas(): Int = idiomas!!.size

  fun esIsla(): Boolean = return paisesLimitrofes!!.isEmpty()

  fun densidadPoblacional(): Int = return (poblacion / superficie).roundToInt()

  fun vecinoMasPoblado(): Pais? = return paisesLimitrofes!!.maxByOrNull{it.poblacion}

  fun limitaCon(pais: Pais): Boolean = return paisesLimitrofes!!.contains(pais)

  fun necesitaTraduccionCon(pais: Pais): Boolean = return this.idiomas != pais.idiomas

  fun aliadoPotencialCon(pais: Pais): Boolean = return this.necesitaTraduccionCon(pais).not() and this.bloquesRegionales!!.contains(pais.bloquesRegionales!!.any())

  fun convieneComprar(pais: Pais): Boolean = return pais.cotizacionDolar!! > this.cotizacionDolar!!

  fun cotizacionAMonedaDe(pais: Pais): Double = return (1 / this.cotizacionDolar!!) * pais.cotizacionDolar!!
}

object Observatorio {
  val paises: List<Pais> = mutableListOf()

  fun sonLimitrofes(pais1: String, pais2: String): Boolean = paises.first{it.nombre == pais1}.limitaCon(paises.first{it.nombre == pais2})
  fun necesitanTraduccion(pais1: String, pais2: String): Boolean = paises.first{it.nombre == pais1}.necesitaTraduccionCon(paises.first{it.nombre == pais2})
  fun sonPotencialesAliados(pais1: String, pais2: String): Boolean = paises.first{it.nombre == pais1}.aliadoPotencialCon(paises.first{it.nombre == pais2})
  fun sePuedeComprarDe_En_(pais1: String, pais2: String): Boolean = paises.first{it.nombre == pais1}.convieneComprar(paises.first{it.nombre == pais2})
  fun cotizarMonedaDe_A_(pais1: String, pais2: String): Double = paises.first{it.nombre == pais1}.cotizacionAMonedaDe(paises.first{it.nombre == pais2})

  fun paisesPorDensidad() = paises.sortedByDescending { it.densidadPoblacional() }
  fun cincoPaisesMayorDensidadP() = paisesPorDensidad().map{it.codigoISO3}.take(5)

  fun continentes() = paises.groupBy { it.continente }
  fun continenteMasPlurinacional() = continentes().mapValues{p->p.value.sumOf{it.cantIdiomas()}}.maxByOrNull{it.value}!!.key

  fun paisesInsulares() = paises.filter{it.esIsla()}
  fun promedioDensidadIslas(): Double = paisesInsulares().map{it.densidadPoblacional().toDouble()}.sum() / paisesInsulares().size
}
