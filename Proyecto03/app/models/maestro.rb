# -*- coding: utf-8 -*-
class Maestro < ActiveRecord::Base
  has_many :comentarios, dependent: :destroy
  attr_accessor :remember_token, :activacion_token
  before_save {self.correo = correo.downcase }
  before_create :crear_activacion_digest
  validates(:nombre, presence: true)
  validates(:correo, presence: true, uniqueness: { case_sensitive: false})
  has_secure_password
  validates(:password, length: { minimum: 6 }, allow_blank: true )

  #Método para buscar a un maestro por su nombre
  def Maestro.search(query)
    where("nombre like ?", "%#{query}%")
  end

  #Método para generar contraseña al maestro
  def Maestro.new_token
    SecureRandom.urlsafe_base64
  end

  #Método para generar una contraseña secreta
  def Maestro.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST : BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
  end
  
  #Método para recordar al maestro
  def remember
    self.remember_token = Maestro.new_token
    update_attribute(:remember_digest, Maestro.digest(remember_token))
  end

  #Método para activar un profesor
  def activar
    update_attribute(:activado, true)
    update_attribute(:activado_a, Time.zone.now)
  end
  
  #Método para enviar el correo
  def enviar_correo_activacion
    MaestroMailer.cuenta_activacion(self).deliver_now
  end

  #Método para saber si un profesor tiene contraseña correcta
  def authenticated?(atributo, token)
    digest = send("#{atributo}_digest")
    return false if digest.nil?
    BCrypt::Password.new(digest).is_password?(token)
  end
  
  #Método para olvidar a profesor
  def forget
    update_attribute(:remember_digest, nil)
  end
  
  #Crea y asigna un número de activación.
  def crear_activacion_digest
    self.activacion_token = Maestro.new_token
    self.activacion_digest = Maestro.digest(activacion_token)
  end
  
end
