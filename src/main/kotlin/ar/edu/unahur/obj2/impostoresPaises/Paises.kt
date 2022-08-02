import kotlin.math.roundToInt

// > Clase "País" definida, con sus componentes:
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
// > Patrón de diseño detectado: Composite (estructural).
// > Fuente: "paisesLimitrofes".
// > Detalles: lista de otros objetos de clase "País". Se accede a ellos y a sus "paisesLimitrofes" de forma recursiva.
{
// >Función auxiliar "cantIdiomas()": devuelve fácilmente la cantidad de elementos en la lista "idiomas".
  fun cantIdiomas(): Int = idiomas.size

// > Función "esPlurinacional": accede a la lista "idiomas" mediante la función auxiliar "cantIdiomas()".
  fun esPlurinacional(): Boolean {
    return cantIdiomas() > 1
  }

// > Función "esIsla()": devuelve True si la lista "paisesLimitrofes" está vacía.
  fun esIsla(): Boolean {
    return paisesLimitrofes.isEmpty()
  }

// > Función "densidadPoblacional()": calcula "poblacion" dividida por "superficie", y convierte el resultado en un Entero (Integer).
  fun densidadPoblacional(): Int {
    return (poblacion / superficie).roundToInt()
  }

// > Función "vecinoMasPoblado()": devuelve el elemento "Pais" dentro de "paisesLimitrofes" que mayor "poblacion" tenga, incluyendo al elemento consultado.
// > Devuelve el mismo elemento consultado en caso de estar vacío o tener mayor "poblacion" que sus "paisesLimitrofes".
  fun vecinoMasPoblado(): Pais {
    var masPoblado = this
    paisesLimitrofes.forEach() {if(it.poblacion > masPoblado.poblacion) masPoblado = it}
    return masPoblado
  }

// > Las siguientes funciones toman un elemento de clase "Pais" como parámetro.

// > Función "limitaCon(pais)": devuelve True si el "Pais" se encuentra dentro de la lista "paisesLimitrofes" del elemento consultado.
  fun limitaCon(pais: Pais): Boolean {
    return paisesLimitrofes.contains(pais)
  }

// > Función "necesitaTraduccionCon(pais)": realiza una intersección entre la lista "idiomas" del "Pais" consultado y la del "Pais" parametrizado.
// > Devuelve True si el conjunto de la intersección está vacío (no hay coincidencias entre las dos listas).
  fun necesitaTraduccionCon(pais: Pais): Boolean {
    return idiomas.toSet().intersect(pais.idiomas.toSet()).isEmpty()
  }

// > Función auxiliar "compartenBloqueRegional()": realiza una intersección entre la lista "bloquesRegionales" del "Pais" consultado y la del "Pais" parametrizado.
// > Devuelve True si el conjunto de la intersección no está vacío (hay al menos una coincidencia entre las dos listas).
  fun compartenBloqueRegional(pais: Pais): Boolean {
    return bloquesRegionales.toSet().intersect(pais.bloquesRegionales.toSet()).isNotEmpty()
  }

// > Función "aliadoPotencialCon(pais)": devuelve True si la función "compartenBloqueRegional(pais)" da True, y la función "necesitaTraduccionCon(pais)" da False
  fun aliadoPotencialCon(pais: Pais): Boolean {
    return this.compartenBloqueRegional(pais) && !this.necesitaTraduccionCon(pais)
  }

// > Función "convieneComprarEn(pais)": devuelve True si la "cotizacionDolar" del "Pais" parametrizado es mayor que la del consultado.
  fun convieneComprarEn(pais: Pais): Boolean {
    return pais.cotizacionDolar > this.cotizacionDolar
  }

// > Función "cotizacionAMonedaDe(monto, pais)": convierte el "monto" ingresado a dólares mediante la "cotizacionDolar" del "Pais" consultado, y luego lo convierte a la moneda del "Pais" parametrizado.
// > Devuelve el resultado ("monto" convertido a la moneda del "Pais" parametrizado).
  fun cotizacionAMonedaDe(monto: Double, pais: Pais): Double {
    return (monto / this.cotizacionDolar) * pais.cotizacionDolar
  }
}

// > Objeto "Observatorio" definido.
object Observatorio {

  val paises = mutableSetOf<Pais>()
// > Patrón de diseño detectado: Singleton (creacional).
// > Fuente: "paises".
// > Detalles: Una vez creado un objeto de clase "Pais" es integrado a la lista "paises" perteneciente al "Observatorio".
// > Desde allí se accede a sus componentes y funciones.

// > Función auxiliar "contienePais(pais)": toma un "nombre" (String) y devuelve True si la lista "paises" contiene un elemento al que le pertenezca.
  fun contienePais(pais: String) = paises.any{it.nombre == pais}

// > Función auxiliar "nombreDePais(pais)": toma un "nombre" (String) y realiza un "check" con la función "contienePais(pais)".
  fun nombreDePais(pais: String): Pais {
    check (contienePais(pais)) {
// > Lanza un error si el "check" falla.
      "El pais $pais no está registrado"
    }
// > Si el "check" no falla, devuelve el "Pais" al que le pertenece el String.
    return paises.first{it.nombre == pais}
  }
// > Función "sonLimitrofes(pais1, pais2)": toma como parámetros dos "nombres" (String) para trabajar con los objetos de clase "Pais" que les pertenecen.
// > Accede a la función "limitaCon(pais)" de "pais1", tomando como parámetro a "pais2".
  fun sonLimitrofes(pais1: String, pais2: String): Boolean = nombreDePais(pais1).limitaCon(nombreDePais(pais2))

// > Función "necesitanTraduccion(pais1, pais2)": toma como parámetros dos "nombres" (String) para trabajar con los objetos de clase "Pais" que les pertenecen.
// > Accede a la función "necesitaTraduccionCon(pais)" de "pais1", tomando como parámetro a "pais2".
  fun necesitanTraduccion(pais1: String, pais2: String): Boolean = nombreDePais(pais1).necesitaTraduccionCon(nombreDePais(pais2))

// > Función "sonPotencialesAliados(pais1, pais2)": toma como parámetros dos "nombres" (String) para trabajar con los objetos de clase "Pais" que les pertenecen.
// > Accede a la función "aliadoPotencialCon(pais)" de "pais1", tomando como parámetro a "pais2".
  fun sonPotencialesAliados(pais1: String, pais2: String): Boolean = nombreDePais(pais1).aliadoPotencialCon(nombreDePais(pais2))

// > Función "sePuedeComprarDe_En_(pais1, pais2)": toma como parámetros dos "nombres" (String) para trabajar con los objetos de clase "Pais" que les pertenecen.
// > Accede a la función "convieneComprarEn(pais)" de "pais1", tomando como parámetro a "pais2".
  fun sePuedeComprarDe_En_(pais1: String, pais2: String): Boolean = nombreDePais(pais1).convieneComprarEn(nombreDePais(pais2))

// > Función "cotizarMonedaDe_A_(pais1, pais2, monto)": toma como parámetros dos "nombres" (String) para trabajar con los objetos de clase "Pais" que les pertenecen.
// > Accede a la función "cotizacionAMonedaDe(monto, pais)" de "pais1", tomando como parámetro a "pais2".
  fun cotizarMonedaDe_A_(pais1: String, pais2: String, monto: Double): Double = nombreDePais(pais1).cotizacionAMonedaDe(monto, nombreDePais(pais2))

// > Función auxiliar "paisesPorDensidad()": devuelve un conjunto ordenado de mayor a menor de los elementos en "paises", según su "densidadPoblacional()"
  fun paisesPorDensidad() = paises.sortedByDescending { it.densidadPoblacional() }
// > Función "cincoPaisesMayorDensidad()": devuelve un conjunto de los "codigoISO3" de los 5 objetos "Pais" con mayor "densidadPoblacional"
  fun cincoPaisesMayorDensidadP() = paisesPorDensidad().map{it.codigoISO3}.take(5)

// > Función auxiliar "continentes()": devuelve un conjunto agrupando los elementos en "paises", por "continente".
  fun continentes() = paises.groupBy { it.continente }
// > Función "continenteMasPlurinacional()": devuelve el "continente" (String) al que pertenezca la mayor cantidad de elementos "plurinacionales" ("cantIdiomas()").
  fun continenteMasPlurinacional() = continentes().mapValues{pais->pais.value.sumOf{it.cantIdiomas()}}.maxByOrNull{it.value}!!.key

// > Función auxiliar "paisesInsulares()": devuelve un conjunto de los elementos en "paises" que cumplan la condición "esIsla()".
  fun paisesInsulares() = paises.filter{it.esIsla()}
// > Función "promedioDensidadIslas()": devuelve un promedio de "densidadPoblacional()" de los elementos en "paises" que cumplen con la condición "esIsla()".
  fun promedioDensidadIslas(): Int = (paisesInsulares().sumOf { it.densidadPoblacional().toDouble() } / paisesInsulares().size).toInt()
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
