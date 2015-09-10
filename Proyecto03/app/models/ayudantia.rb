# -*- coding: utf-8 -*-
class Ayudantia < ActiveRecord::Base
  self.table_name = "ayudantias"
  belongs_to :maestro
  belongs_to :estudiante
  validates :estudiante_id, presence: true
  validates :maestro_id, presence: true

  #Definimos la búsqueda de los ayudantes de un maestro en particular
  def Ayudantia.ayudantes_de(maestro)
    ayu = Ayudantia.where("maestro_id = ?", maestro.id)
    arr = Array.new
    i = 0
    while i < ayu.count do
      arr.push(Estudiante.find(ayu[i].estudiante_id))
      i+= 1
    end 
    return arr
  end

  #Definimos las búsquedas de los ayudantes de un maestro en particular
  def Ayudantia.ayudados_por(estudiante)
    ayu = Ayudantia.where("estudiante_id = ?", estudiante.id).order(:created_at).reverse
    arr = Array.new
    i = 0
    while i < ayu.count do
      arr.push(Maestro.find(ayu[i].maestro_id))
      i += 1
    end
    return arr
  end
  
end
