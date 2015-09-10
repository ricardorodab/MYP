# -*- coding: utf-8 -*-
class EstudiantesController < ApplicationController
  respond_to :html, :json, :js
  before_action :es_un_usuario, only: [:edit, :update, :destroy, :show]
  before_action :es_el_usuario, only: [:edit, :update]
  before_action :es_admin, only: [:destroy, :estadisticas] 

  #Método para mostrar el perfil del estudiante
  def show
    @estudiante = Estudiante.find(params[:id])
  end

  #Método para registrar un estudiante nuevo
  def create
    @estudiante = Estudiante.new(estudiante_params)
    if (Estudiante.where("correo LIKE ?", "#{@estudiante.correo}@ciencias.unam.mx")).first == nil
      @estudiante.update_attribute(:correo, @estudiante.correo+"@ciencias.unam.mx")
      if @estudiante.save_with_captcha
        log_in @estudiante
        @estudiante.enviar_correo_activacion
        flash.now[:info] = "Por favor, revisa tu correo para activar tu cuenta."
        log_out
        respond_with @estudiante do |format|
          format.html {redirect_to registro_path, notice: "Espera tu correo para activar y acceder a tu cuenta. Posiblemente tu correo llegue a spam. Te esperamos pronto"}
        end
      else
        render 'new'
      end
    else
      respond_with @estudiante do |format|
        format.html {redirect_to registro_path, notice: "Correo repetido"}
      end
    end
  end
  
  #Método para crear un nuevo estudiante en la base
  def new
    @estudiante = Estudiante.new
  end
  
  #Método para editar los datos del estudiante
  def edit
    @estudiante = Estudiante.find(params[:id])
  end

  #Método para actualizar los datos del estudiante
  def update
    @estudiante = Estudiante.find(params[:id])
    if @estudiante.update_attributes(estudiante_params)
      flash[:succes] = "Perfil actualizado"
      redirect_to @estudiante
    else
      render 'edit'
    end
  end
  
  #Método auxiliar para saber si es el usuario quien manda a llamar a un controlador
  def es_el_usuario
    @estudiante = Estudiante.find(params[:id])
    redirect_to(root_url) unless @estudiante == estudiante_actual
  end

  #Método auxiliar para saber si es un usuario de la base
  def es_un_usuario
    unless conectado?
      flash[:danger] = "Favor de iniciar sesión"
      redirect_to login_url
    end
  end

  #Método para mostrar en una lista los alumnos que existen
  def index
    if params[:search]
      @estudiantes = Estudiante.search(params[:search]).paginate(page: params[:page])
    else
      if estudiante_actual.activado
        @estudiantes = Estudiante.paginate(page: params[:page])
      else
        redirect_to login_url
      end
    end
  end
  
  #Método para crear un nuevo ayudante
  def ayudante_nuevo
    @maestro = Maestro.find(params[:maestro_id])
    if params[:search_ayudante]
      @estudiantes = Estudiante.search(params[:search_ayudante]).paginate(page: params[:page])
    else      
      @estudiantes = Estudiante.paginate(page: params[:page])
    end
  end

  #Método para eliminar una estudiante
  def destroy
    Estudiante.find(params[:id]).destroy
    flash[:succes] = "Estudiante eliminado"
    redirect_to estudiantes_url
  end

  #Método para activar un estudiante y hacerlo válido
  def activar
    update_attribute(:activado, true)
    update_attribute(:activado_a, Time.zone.now)
  end

  #Método auxiliar en la activación del estudiante por correo
  def enviar_correo_activacion
    EstudianteMailer.cuenta_activacion(self).deliver_now
  end

  #Método que saca una pequeña coleacción de los asuntos por hacer del aministrador
  def estadisticas
    @estudiante = Estudiante.find(params[:estudiantes_id])
    @comentarios = (Comentario.where("warning = ?", true)).paginate(page: params[:page], per_page: 4)
    @maestros = (Maestro.where("aprobado = ?", false)).paginate(page: params[:page], per_page: 8)
    @ayudantes = (Ayudantia.where("peticion_eliminar = ?", true)).paginate(page: params[:page], per_page: 10)
    @no_activados = (Maestro.where("correo_enviado = ?", false)).paginate(page: params[:page], per_page: 8)
  end

  #Métodos privados para manejo de estudiantes.
  private
  
  #Método para manejar parámetros fuertes.
  def estudiante_params
    params.require(:estudiante).permit(:nombre, :correo, :password,
                                 :password_confirmation, :captcha, :captcha_key)
  end

  #método para saer si un estudiante tiene permisos de administrador
  def es_admin
    redirect_to(root_url) unless estudiante_actual.admin?
  end
end
