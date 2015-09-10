class AddAyudanteAEstudiantes < ActiveRecord::Migration
  def change
    add_column :estudiantes, :ayudante, :boolean, default: false
  end
end
