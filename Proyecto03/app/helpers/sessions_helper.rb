# -*- coding: utf-8 -*-
module SessionsHelper
  
  #Inicia sesión al usuario que lo llama
  def log_in(estudiante)
    if estudiante.is_a?(Estudiante)
      session[:estudiante_id] = estudiante.id
    else
      session[:maestro_id] = estudiante.id
    end
  end

  #Método que cierra sesión
  def log_out
    forget(estudiante_actual)
    session.delete(:estudiante_id)
    @estudiante_actual = nil
  end

  #Método para olvidar al estudiante
  def forget(estudiante)
    estudiante.forget
    cookies.delete(:estudiante_id)
    cookies.delete(:remember_token)
  end

  #Método para acordarse del estudiante
  def remember(estudiante)
    estudiante.remember
    cookies.permanent.signed[:estudiante_id] = estudiante.id
    cookies.permanent[:remember_token] = estudiante.remember_token
  end

  #Regresa el estudiante actual
  def estudiante_actual
    if(estudiante_id = session[:estudiante_id])
      @estudiante_actual ||= Estudiante.find_by(id: estudiante_id)
      elsif (estudiante_id = cookies.signed[:estudiante_id])
      estudiante = Estudiante.find_by(id: estudiante_id)
      if estudiante && estudiante.authenticated?(:remember, cookies[:remember_token])
       log_in estudiante
       @estudiante_actual = estudiante
       end
    end
  end

  #Regresa el nombre del estudiante
    def nombre_estudiante_actual
      @estudiante_actual ||= Estudiante.find_by(id: session[:estudiante_id])
    return @estudiante_actual.nombre
  end

  #Metodo para saber si está conectado
  def conectado?
    !estudiante_actual.nil?
  end

  #método para saber si el estudiante que recibe es el mismo que el conectado
  def es_estudiante_actual?(estudiante)
    estudiante == estudiante_actual
  end

  #Método para saber si el estudiante actual es ayudante de un maestro que recibe
  def es_ayudante_de(maestro_id)
    if !conectado?
      return false
    end
    arr = Ayudantia.ayudantes_de(Maestro.find(maestro_id))
    i = 0
    while i < arr.count do
      if arr[i].id == estudiante_actual.id
        return true
      end
      i += 1
    end
    return false
  end
  
end

