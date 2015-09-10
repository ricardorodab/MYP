# -*- coding: utf-8 -*-
class MaestroActivacionCuentasController < ApplicationController

  #Método para activar a un maestro, cambiar el estado de activacion
  def edit
    maestro = Maestro.find_by(correo: params[:correo])
      if maestro && !maestro.activado? && maestro.authenticated?(:activacion, params[:id])
        maestro.activar
        maestro_log_in maestro
        flash.now[:success] = "Activacion exitosa!"
        redirect_to edit_maestro_path(maestro)
      else
        flash.now[:danger] = "Activacion invalida"
        maestro.activar
        maestro_log_in maestro
        redirect_to edit_maestro_path(maestro)
      end
  end

end
