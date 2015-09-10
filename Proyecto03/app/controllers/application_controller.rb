# -*- coding: utf-8 -*-
class ApplicationController < ActionController::Base
  
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  include SessionsHelper
  include SimpleCaptcha::ControllerHelpers
  include Sprockets::Rails::Helper
  include MaestroSessionsHelper
  private

  #Método para saber si es un usuario de la aplicacion
  def es_un_usuario
    unless conectado?
      flash[:danger] = "Favor de iniciar sesión"
      redirect_to login_url
    end
  end

  #Método para saber si está conectado un usuario
  def se_conecto
    unless conectado? || maestro_conectado?
      flash[:danger] = "Favor de iniciar sesión"
      redirect_to root_path
    end
  end
end
