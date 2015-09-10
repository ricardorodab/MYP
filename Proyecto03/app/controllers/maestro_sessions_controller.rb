# -*- coding: utf-8 -*-
class MaestroSessionsController < ApplicationController
  #Método para crear una nueva sesión de maestros.
  def new
  end

  #Crea una sesión se maestros
  def create
    maestro = Maestro.find_by(correo: (params[:maestro_session][:correo]+"@ciencias.unam.mx").downcase)
    if maestro && maestro.authenticate(params[:maestro_session][:password])
      #if maestro.activado?
        maestro_log_in maestro
        params[:maestro_session][:remember_me] == '1' ? maestro_remember(maestro) : maestro_forget(maestro)
        redirect_to maestro
    else
      flash.now[:danger] = 'Contraseña/correo incorrecto'
      render 'new'
    end
  end
  
  #Cierra la sesión de un maestro
  def destroy
    maestro_log_out if maestro_conectado?
    redirect_to root_url
  end
end
