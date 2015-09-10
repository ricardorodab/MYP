require 'test_helper'

class EstudianteMailerTest < ActionMailer::TestCase
  test "cuenta_activacion" do
    mail = EstudianteMailer.cuenta_activacion
    assert_equal "Cuenta activacion", mail.subject
    assert_equal ["to@example.org"], mail.to
    assert_equal ["from@example.com"], mail.from
    assert_match "Hi", mail.body.encoded
  end

  test "restablecer_password" do
    mail = EstudianteMailer.restablecer_password
    assert_equal "Restablecer password", mail.subject
    assert_equal ["to@example.org"], mail.to
    assert_equal ["from@example.com"], mail.from
    assert_match "Hi", mail.body.encoded
  end

end
