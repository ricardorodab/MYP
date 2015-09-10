# -*- coding: utf-8 -*-
class AyudantiasController < ApplicationController
  before_action :es_un_usuario, only: [:ayudantes_create, :create]
  before_action :es_admin, only: [:ayudante_destroy] 
  
  #Método para crear una relacion de ayudantía entre profesor alumno.
  def create
    @ayudantia = Ayudantia.new(ayudantia_params)
    if @ayudantia.save
      if conectado?
        render estudiante_actual
      elsif maestro_conectado?
        render maestro_actual
      end
    end
  end
  
  #Método para crear un nuevo ayudante
  def new
    @ayudantia = Ayudantia.new
  end

  #Método para dar de baja y eliminar la relación profesor-estudiante
  def baja_ayudante
    @ayudantia = Ayudantia.where("estudiante_id = ? AND maestro_id = ?", params[:estudiante_id], params[:maestro_id]).first
    @ayudantia.update_attribute(:peticion_eliminar, true)
    redirect_to estudiante_actual
  end


  #Método para ignorar una solicitud de baja
  def mantener_ayudante
    @ayudantia = Ayudantia.where("estudiante_id = ? AND maestro_id = ?", params[:estudiante_id], params[:maestro_id]).first
    @ayudantia.update_attribute(:peticion_eliminar, false)
    redirect_to estudiante_actual
  end
    
  #Método para crear una relación profesor-estudiante
  def ayudantes_create
    @estudiante = Estudiante.find(params[:estudiante])
    @maestro = Maestro.find(params[:maestro_id]) 
    ayudantia = Ayudantia.new
    ayudantia.maestro_id = @maestro.id
    ayudantia.estudiante_id = @estudiante.id
    if ayudantia.save
      if !@estudiante.ayudante
        @estudiante.update_attribute(:ayudante, true)
      end
      redirect_to @maestro
    end
  end
  
  #Método para eliminar una relación prodesor-estudiante
  def ayudante_destroy
    @estudiante = Estudiante.find(params[:estudiante_id])
    @maestro = Maestro.find(params[:maestro_id]) 
    Ayudantia.where("estudiante_id = ? AND maestro_id = ?", @estudiante.id, @maestro.id).first.destroy
    redirect_to @maestro
  end
  
    
  #Métodos privador para ayudarnos con el manejo de ayudantes
  private
  
  #Usamos parámetros "fuertes" para creación de ayudantes
  def ayudantia_params
    params.require(:ayudantia).permit(:maestro_id, :estudiante_id)
  end
      
  #Método para ver es un usario de la aplicación
  def es_un_usuario
    unless conectado? || maestro_conectado?
      flash[:danger] = "Favor de iniciar sesión"
      redirect_to login_url
    end
  end

  #Método para saber si es un administrador
  def es_admin
    redirect_to(root_url) unless conectado? && estudiante_actual.admin?
  end

end
