# -*- coding: utf-8 -*-
class ActivacionCuentasController < ApplicationController

  #Método para activar una cuenta, modificar el parámetro de activado a true
  def edit
    estudiante = Estudiante.find_by(correo: params[:correo])
    if estudiante && !estudiante.activado? && estudiante.authenticated?(:activacion, params[:id])
      estudiante.activar
      log_in estudiante
      flash.now[:success] = "Activacion exitosa!"
      redirect_to estudiante
    else
      flash.now[:danger] = "Activacion invalida"
      redirect_to root_url
    end
  end
end
