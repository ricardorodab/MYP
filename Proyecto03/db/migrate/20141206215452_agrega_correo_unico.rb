class AgregaCorreoUnico < ActiveRecord::Migration
  def change
    add_index :estudiantes, :correo, unique: true
  end
end
