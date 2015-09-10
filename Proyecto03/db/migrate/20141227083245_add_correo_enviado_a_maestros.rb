class AddCorreoEnviadoAMaestros < ActiveRecord::Migration
  def change
    add_column :maestros, :correo_enviado, :boolean, default: false
    add_column :maestros, :registrado, :boolean, default: false
  end
end
