class AddGradoAMaestros < ActiveRecord::Migration
  def change
    add_column :maestros, :grado, :string
    add_column :maestros, :aprobado, :boolean, default: false
  end
end
