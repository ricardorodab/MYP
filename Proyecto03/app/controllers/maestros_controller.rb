# -*- coding: utf-8 -*-
class MaestrosController < ApplicationController

  before_action :es_admin, only: [:destroy, :aprobar] 

  #Método para crear un nuevo maestro en la base
  def new
    @maestro = Maestro.new
  end

  #Método para aprobar un profesor en la base de datos.
  def aprobar
    @maestro = Maestro.find(params[:maestro_id])
    @maestro.update_attribute(:aprobado, true)
    #flash.now[:success] = @maestro.nombre + " aprobado"
      #redirect_to '/estudiantes/'+params[:estudiante_id]+'/estadisticas'
    redirect_to @maestro
  end
  
  #Método para crea un nuevo maestro y registrarlo
  def create
    @maestro = Maestro.new(maestro_params)
    @maestro.update_attribute(:correo, @maestro.correo+"@ciencias.unam.mx")
    if conectado? || maestro_conectado?
      @maestro.password_digest = SecureRandom.urlsafe_base64
      if @maestro.save        
        flash.now[:succes] = "Registro exitoso, favor de esperar que el profesor sea aprobado por algún administrador."
        redirect_to maestros_path
      else
        render 'new'
      end
    else
        if !(conectado? || maestro_conectado?)
          @maestro.password_digest = SecureRandom.urlsafe_base64
          if @maestro.save
            redirect_to root_path
          end
        else
          if @maestro.save
            maestro_log_in @maestro
            flash.now[:succes] = "Registro exitoso, favor de esperar que el profesor sea aprobado por algún administrador."
            redirect_to maestros_path
          else
            render 'new'
          end
        end
    end
  end

  #Método para editar un profesor
  def edit
    @maestro = Maestro.find(params[:id])
  end

  #Método para actualizar los datos del profesor
  def update
    @maestro = Maestro.find(params[:id])
    if @maestro.update_attributes(maestro_params)
      flash[:success] = "Maestro Actualizado"
      redirect_to @maestro
    else
      render 'edit'
    end
  end

  #método para mostrar los profesores que existen
  def index
    if params[:search]
      @maestros = Maestro.search(params[:search]).paginate(page: params[:page])
    else
    @maestros = Maestro.paginate(page: params[:page])
    end
  end

  #Método para mostrar a un profesor del sistema
  def show
    @maestro = Maestro.find(params[:id])
    @comentarios = @maestro.comentarios.paginate(page: params[:page], :per_page => 10)
    @comentario = estudiante_actual.comentarios.build if conectado?
  end
  
  #Método para eliminar un profesor
  def destroy
    Maestro.find(params[:id]).destroy
    flash[:succes] = "Maestro eliminado"
    redirect_to maestros_path
  end

  #Método para enviar un correo al profesor
  def env_cor_act
    @maestro = Maestro.find(params[:maestro_id])
    if @maestro.aprobado?
      maestro_log_in @maestro
      @maestro.enviar_correo_activacion
      @maestro.update_attribute(:correo_enviado, true)
      maestro_log_out
      flash.now[:success] = "Correo de activacion enviado a " + @maestro.correo
      #redirect_to '/estudiantes/'+params[:estudiante_id]+'/estadisticas'
      redirect_to @maestro
    else
      flash.now[:danger] = "Favor de aprobar a "+ @maestro.nombre + " antes de enviar correo de activacion"
      #redirect_to '/estudiantes/'+params[:estudiante_id]+'/estadisticas'
      redirect_to @maestro
    end
  end

  #Método para activar un profesor
  def activar
    update_attribute(:activado, true)
    update_attribute(:activado_a, Time.zone.now)
  end

  private
  #Método para usar parámetros fuertes con los maestros
  def maestro_params
    params.require(:maestro).permit(:nombre, :correo, :grado, :password,
                                       :password_confirmation)
  end
  #Método para saber si el estudiante actual es administrador
  def es_admin
    redirect_to(root_url) unless estudiante_actual.admin?
  end
end
