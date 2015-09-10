# -*- coding: utf-8 -*-
class Comentario < ActiveRecord::Base
  belongs_to :estudiante
  belongs_to :maestro
  default_scope -> { order(created_at: :desc) }
  validates :estudiante_id, presence: true
  validates :comentario, presence: true, length: { maximum: 300 }
  validates :maestro_id, presence: true

  #Buscamos a los comentarios de un estudiante
  def Comentario.califico_a(estudiante)
    com = Comentario.where("estudiante_id = ?", estudiante.id).order(:created_at)
    arr = Array.new
    i = 0
    while i < com.count do
      arr.push(Maestro.find(com[i].maestro_id))
      i += 1
    end
    return arr
  end
  
  #Método que nos dice si un estudiante ya calificó a un profesor
  def Comentario.ya_califico(estudiante, maestro)
    com = Comentario.where("estudiante_id = ? AND maestro_id = ?", estudiante.id, maestro.id)
    if com.count > 0
      return true
    else
      return false
    end
  end
end
