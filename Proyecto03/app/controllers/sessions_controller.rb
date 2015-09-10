# -*- coding: utf-8 -*-
class SessionsController < ApplicationController
  before_action :revisa_conectado, only: [:destroy]
  
  #Define una nueva sesión en la base (tabla inexistente)
  def new
  end

  #Crea una nueva sesion (login)
  def create
    estudiante = Estudiante.find_by(correo: (params[:session][:correo]+"@ciencias.unam.mx").downcase)
    if estudiante && estudiante.authenticate(params[:session][:password])
      if estudiante.activado?
        log_in estudiante
        params[:session][:remember_me] == '1' ? remember(estudiante) : forget(estudiante)
        redirect_to estudiante
      else
        mensaje = "Cuenta no activada. "
        mensaje += "Revisa tu correo para visitar el link de activacion."
        flash.now[:warning] = mensaje
        render 'new'
      end
    else
      flash.now[:danger] = 'Contraseña/correo incorrecto'
      render 'new'
    end
  end
  
  #Cierra una sesión (logout)
  def destroy
    log_out if conectado?
    redirect_to root_url
  end

  private
  #Nos dice si se encuentra un estudiante conectado
  def revisa_conectado
    unless conectado?
      redirect_to root_path
    end
  end
end
