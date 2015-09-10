# Preview all emails at http://localhost:3000/rails/mailers/estudiante_mailer
class EstudianteMailerPreview < ActionMailer::Preview

  # Preview this email at http://localhost:3000/rails/mailers/estudiante_mailer/cuenta_activacion
  def cuenta_activacion
    EstudianteMailer.cuenta_activacion
  end

  # Preview this email at http://localhost:3000/rails/mailers/estudiante_mailer/restablecer_password
  def restablecer_password
    EstudianteMailer.restablecer_password
  end

end
