# -*- coding: utf-8 -*-
module MaestroSessionsHelper

  #Inicia sesión al usuario que lo llama
  def maestro_log_in(maestro)
    if maestro.is_a?(Maestro)
      session[:maestro_id] = maestro.id
    else
      session[:maestro_id] = maestro.id
    end
  end

  #Cierra sesión al maestro que lo llama
  def maestro_log_out
    maestro_forget(maestro_actual)
    session.delete(:maestro_id)
    @maestro_actual = nil
  end

  #Método que busca un maestro de la base con un id.
  def busca_maestro(int)
    return Maestro.find(int)
  end

  #Método que olvida a un maestro al cerrar sesión
  def maestro_forget(maestro)
    maestro.forget
    cookies.delete(:maestro_id)
    cookies.delete(:remember_token)
  end

  #Método para recordar al maestro que inició sesión
  def maestro_remember(maestro)
    maestro.remember
    cookies.permanent.signed[:maestro_id] = maestro.id
    cookies.permanent[:remember_token] = maestro.remember_token
  end

  #Regresa el maestro actual
  def maestro_actual
    if(maestro_id = session[:maestro_id])
      @maestro_actual ||= Maestro.find_by(id: maestro_id)
      elsif (maestro_id = cookies.signed[:maestro_id])
      maestro = Maestro.find_by(id: maestro_id)
      if maestro && maestro.authenticated?(:remember, cookies[:remember_token])
       maestro_log_in maestro
       @maestro_actual = maestro
       end
    end
  end

  #Regresa el nombre del maestro
    def nombre_maestro_actual
      @maestro_actual ||= Maestro.find_by(id: session[:maestro_id])
    return @maestro_actual.nombre
  end

  #Metodo para saber si está conectado
  def maestro_conectado?
    !maestro_actual.nil?
  end

  #Método para saber si el maestro que recibe es el maestro actual
  def es_maestro_actual?(maestro)
    maestro == maestro_actual
  end

end

