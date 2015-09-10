class EstudianteMailer < ActionMailer::Base
  default from: "no-reply@elprometeo.com"

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.estudiante_mailer.cuenta_activacion.subject
  #
  def cuenta_activacion(estudiante)
    @estudiante = estudiante    
    mail to: estudiante.correo, subject: "Activa tu cuenta"
  end

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.estudiante_mailer.restablecer_password.subject
  #
  def restablecer_password
    @greeting = "Hi"

    mail to: "to@example.org"
  end
end
