package ar.edu.unahur.obj2.caralibro

import kotlin.math.ceil

class Usuario {
  val publicaciones = mutableListOf<Publicacion>()
  var amigos= mutableListOf<Usuario>()
  var listaDePermitidos= mutableListOf<Usuario>()
  var listaDeExcluidos= mutableListOf<Usuario>()
  fun agregarAmigo(amigo:Usuario){
    check(!this.amigos.contains(amigo)){"el usuario ingresado ya esta en la lista de amigos"}
    amigos.add(amigo)
  }
  fun agregarUsuarioAListaDePermitidos(usuario: Usuario){
    listaDePermitidos.add(usuario)
  }
  fun agregarUsuarioAListaDeExcluidos(usuario: Usuario){
    listaDeExcluidos.add(usuario)
  }

  fun agregarPublicacion(publicacion: Publicacion) {
    publicaciones.add(publicacion)
  }
  fun darMeGusta(publicacion: Publicacion){
    check(!publicacion.usuariosQueLeGusta.contains(this)){"Este usuario ya le ha dado me gusta a la publicacion"}
    publicacion.usuariosQueLeGusta.add(this)
    publicacion.contadorDeLikes+=1
  }
  fun permiteQue_UsuarioVeaLa_Publicacion(usuarioQueQuiereVerla:Usuario, publicacion: Publicacion)=publicacion.puedeSerVistaPor(usuarioQueQuiereVerla,this )

  fun cantidadDeLikesDePublicacion(publicacion: Publicacion) = publicacion.contadorDeLikes()
  fun espacioDePublicaciones() = publicaciones.sumBy { it.espacioQueOcupa() }
  fun esMasAmistosoQue(usuario:Usuario)= cantidadDeAmigos() > usuario.cantidadDeAmigos()
  fun cantidadDeAmigos() = this.amigos.size
  fun puedeVerTodasLasPublicaciones(usuario:Usuario)=publicaciones.all{it.puedeSerVistaPor(usuario,this)}
  fun mejoresAmigos() = amigos.filter { it.puedeVerTodasLasPublicaciones(this ) }
  fun amigoMasPopular() = amigos.maxBy { it.totalLikes() }
  fun totalLikes() = publicaciones.sumBy { it.contadorDeLikes }

  fun meStalkea(usuario: Usuario) = this.meGustasDeUnUsuarioEnMiPublicacion(usuario) >= this.cantidadPublicacionesParaStalkear()
  fun cantidadPublicacionesParaStalkear() = ceil(this.cantidadPublicaciones() * 0.9).toInt()
  fun cantidadPublicaciones() = publicaciones.size
  fun meGustasDeUnUsuarioEnMiPublicacion(usuario:Usuario) = publicaciones.count { it.leDioLike(usuario)}
}

