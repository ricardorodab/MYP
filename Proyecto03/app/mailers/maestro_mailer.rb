class MaestroMailer < ActionMailer::Base
  default from: "no-reply@elprometeo.com"

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.Maestro_mailer.cuenta_activacion.subject
  #
  def cuenta_activacion(maestro)
    @maestro = maestro    
    mail to: maestro.correo, subject: "Activa tu cuenta de profesor"
  end

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.Maestro_mailer.restablecer_password.subject
  #
  def restablecer_password
    @greeting = "Hi"

    mail to: "to@example.org"
  end
end
