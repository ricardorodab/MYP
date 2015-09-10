class AgregaCorreoUnicoAMaestros < ActiveRecord::Migration
  def change
    add_index :maestros, :correo, unique: true
  end

end
