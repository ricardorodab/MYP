# -*- coding: utf-8 -*-
class ComentariosController < ApplicationController
  before_action :es_un_usuario, only: [:create, :destroy]

  #Método para crear un comentario nuevo
  def new
    @comentario = Comentario.new
  end

  #Método para editar los elementos del comentario (respuesta)
  def edit
    @comentario = Comentario.find(params[:comentarios_id])
    redirect_to Maestro.find(params[:maestros_id]).path
  end

  #Método para crear un comentario nuevo.
  def create
    @comentario = Comentario.new(comentario_params)
    @maestro = Maestro.find(params[:maestro_id])
    @comentario.maestro_id = @maestro.id
    if @comentario.save
      flash[:success] = "Tu comentario ha sido guardado"
      redirect_to @maestro
    else
      render 'new'
    end
  end

  #Método para actualizar el comentario
  def update
    @comentario = Comentario.find(params[:id])
    @maestro = Maestro.find(params[:maestro_id])
    if @comentario.update_attributes(comentario_params)
      flash[:succes] = "Perfil actualizado"
      redirect_to @maestro
    else
      render 'edit'
    end
  end

  #Método para eliminar un comentario
  def destroy
    @comentario = Comentario.find(params[:id]).destroy
    redirect_to busca_maestro(@comentario.maestro_id)
  end

  #Método para reportar un comentario
  def reportar
    @comentario = Comentario.find(params[:comentario_id])
    @comentario.update_attribute(:warning, true)
    if conectado?
      redirect_to busca_maestro(@comentario.maestro_id)
    elsif maestro_conectado?
      redirect_to maestro_actual
    else
      redirect_to root_path
    end
  end  

  #Método para eliminar el reporte de un comentario previamente reportado
  def quitar_reporte
    @comentario = Comentario.find(params[:comentario_id])
    @comentario.update_attribute(:warning, false)
    if conectado?
      redirect_to busca_maestro(@comentario.maestro_id)
    elsif maestro_conectado?
      redirect_to maestro_actual
    else
      redirect_to root_path
    end
  end  

  #Método para que los profesores puedan responder comentarios
  def responder
    @comentario = Comentario.find(params[:comentario_id])
  end
  
  #Método auxiliar
  def show
    redirect_to root_path
  end
  
  private
  #Método para usar parametros fuertes en los comentarios
  def comentario_params
    params.require(:comentario).permit(:comentario,
                                       :estudiante_id,
                                       :maestro_id,
                                       :semestre,
                                       :materia,
                                       :facilidad,
                                       :ayuda,
                                       :claridad,
                                       :aprobo,
                                       :recursador,
                                       :evaluacion,
                                       :recomendacion,
                                       :ayudante_nombre,
                                       :ayudante_comentario,
                                       :respuesta)
  end
end
