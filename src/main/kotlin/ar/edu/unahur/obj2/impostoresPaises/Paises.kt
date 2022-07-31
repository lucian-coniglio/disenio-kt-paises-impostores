import kotlin.math.roundToInt

class Pais (
  val nombre: String,
  val codigoISO3: String,
  val codigoMonedaLocal: String,
  val continente: String,
  val cotizacionDolar: Double,
  val poblacion: Int,
  val superficie: Double,
  var paisesLimitrofes: MutableList<Pais>,
  var bloquesRegionales: List<String>,
  var idiomas: MutableList<String>)
{

  fun esPlurinacional(): Boolean {
    return cantIdiomas() > 1
  }

  fun cantIdiomas(): Int = idiomas.size

  fun esIsla(): Boolean {
    return paisesLimitrofes.isEmpty()
  }

  fun densidadPoblacional(): Int {
    return (poblacion / superficie).roundToInt()
  }

  fun vecinoMasPoblado(): Pais? {
    var masPoblado = this
    paisesLimitrofes.forEach() {if(it.poblacion > masPoblado.poblacion) masPoblado = it}
    return masPoblado
  }

  fun limitaCon(pais: Pais): Boolean {
    return paisesLimitrofes.contains(pais)
  }

  fun necesitaTraduccionCon(pais: Pais): Boolean {
    return idiomas.toSet().intersect(pais.idiomas.toSet()).isEmpty()
  }

  fun compartenBloqueRegional(pais: Pais): Boolean {
    return bloquesRegionales.toSet().intersect(pais.bloquesRegionales.toSet()).isEmpty()
  }

  fun aliadoPotencialCon(pais: Pais): Boolean {
    return !this.necesitaTraduccionCon(pais) && this.compartenBloqueRegional(pais)
  }

  fun convieneComprar(pais: Pais): Boolean {
    return pais.cotizacionDolar > this.cotizacionDolar
  }

  fun cotizacionAMonedaDe(monto: Double, pais: Pais): Double {
    return (monto / this.cotizacionDolar) * pais.cotizacionDolar
  }
}

object Observatorio {
  val paises = mutableSetOf<Pais>()

  fun contienePais(pais: String) = paises.any{it.nombre == pais}

  fun nombreDePais(pais: String): Pais {
    check (contienePais(pais)) {
      "El pais $pais no está registrado"
    }
    return paises.first{it.nombre == pais}
  }

  fun sonLimitrofes(pais1: String, pais2: String): Boolean = nombreDePais(pais1).limitaCon(nombreDePais(pais2))

  fun necesitanTraduccion(pais1: String, pais2: String): Boolean = nombreDePais(pais1).necesitaTraduccionCon(nombreDePais(pais2))

  fun sonPotencialesAliados(pais1: String, pais2: String): Boolean = nombreDePais(pais1).aliadoPotencialCon(nombreDePais(pais2))

  fun sePuedeComprarDe_En_(pais1: String, pais2: String): Boolean = nombreDePais(pais1).convieneComprar(nombreDePais(pais2))

  fun cotizarMonedaDe_A_(pais1: String, pais2: String, monto: Double): Double = nombreDePais(pais1).cotizacionAMonedaDe(monto, nombreDePais(pais2))

  fun paisesPorDensidad() = paises.sortedByDescending { it.densidadPoblacional() }
  fun cincoPaisesMayorDensidadP() = paisesPorDensidad().map{it.codigoISO3}.take(5)

  fun continentes() = paises.groupBy { it.continente }
  fun continenteMasPlurinacional() = continentes().mapValues{pais->pais.value.sumOf{it.cantIdiomas()}}.maxByOrNull{it.value}!!.key

  fun paisesInsulares() = paises.filter{it.esIsla()}
  fun promedioDensidadIslas(): Int = (paisesInsulares().sumOf { it.densidadPoblacional().toDouble() } / paisesInsulares().size).toInt()
}
