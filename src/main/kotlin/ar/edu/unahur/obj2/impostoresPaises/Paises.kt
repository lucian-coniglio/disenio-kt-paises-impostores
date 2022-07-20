
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

  fun esPlurinacional(): Boolean {
    return idiomas!!.size > 1
  }

  fun esIsla(): Boolean {
    return paisesLimitrofes!!.isEmpty()
  }

  fun desidadPoblacional(): Int {
    return (poblacion / superficie).toInt()
  }

  fun vecinoMasPoblado(): Pais? {
    return paisesLimitrofes!!.maxByOrNull{it.poblacion}
  }

  fun limitaCon(pais: Pais): Boolean {
    return paisesLimitrofes!!.contains(pais)
  }

  fun necesitaTraduccionCon(pais: Pais): Boolean {
    return this.idiomas != pais.idiomas
  }

  fun aliadoPotencialCon(pais: Pais): Boolean {
    return this.necesitaTraduccionCon(pais).not() and this.bloquesRegionales!!.contains(pais.bloquesRegionales!!.any())
  }

  fun convieneComprar(pais: Pais): Boolean {
    return pais.cotizacionDolar!! > this.cotizacionDolar!!
  }

  fun cotizacionAMonedaDe(pais: Pais): Double {
    return (1 / this.cotizacionDolar!!) * pais.cotizacionDolar!!
  }
}