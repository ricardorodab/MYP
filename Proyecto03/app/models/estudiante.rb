# -*- coding: utf-8 -*-
class Estudiante < ActiveRecord::Base
  has_many :comentarios, dependent: :destroy
  attr_accessor :remember_token, :activacion_token
  before_save :downcase_correo
  before_create :crear_activacion_digest
  before_save { self.correo = correo.downcase }
  validates(:nombre, presence: true)
  validates(:correo, presence: true, uniqueness: { case_sensitive: false} )
  apply_simple_captcha
  has_secure_password
  validates(:password, length: { minimum: 6 }, allow_blank: true )

  #Definimos un nuevo token para seguridad del estudiante
  def Estudiante.new_token
    SecureRandom.urlsafe_base64
  end

  #Definimos la búsqueda de un estudiante con un nombre en común
  def Estudiante.search(query)
    where("nombre like ?", "%#{query}%")
  end

  #Buscamos a ayudantes con cierto nombre
  def Estudiante.search_ayudante(query)
    where("nombre like ?", "%#{query}%")
  end
  
  #Método para encriptar la contraseña
  def Estudiante.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST : BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
    end

  #Método para rcordar a un estudiante.
  def remember
    self.remember_token = Estudiante.new_token
    update_attribute(:remember_digest, Estudiante.digest(remember_token))
  end

  #Método para identificar a un estudiante
  def authenticated?(atributo, token)
    digest = send("#{atributo}_digest")
    return false if digest.nil?
    BCrypt::Password.new(digest).is_password?(token)
  end

  #Método para olvidar a un estudiante
  def forget
    update_attribute(:remember_digest, nil)
  end
  
  #Método para activar a un estudiante y modificar su status
  def activar
    update_attribute(:activado, true)
    update_attribute(:activado_a, Time.zone.now)
  end

  #Método para enviar un correo de activación
  def enviar_correo_activacion
    EstudianteMailer.cuenta_activacion(self).deliver_now
  end


  #Privados:
  private

  #Convierte el correo a downcase
  
  def downcase_correo
    self.correo = correo.downcase
  end

  #Crea y asigna un número de activación.
  def crear_activacion_digest
    self.activacion_token = Estudiante.new_token
    self.activacion_digest = Estudiante.digest(activacion_token)
  end
end
