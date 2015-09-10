class AddEstudianteAAyudantias < ActiveRecord::Migration
  def change
    add_column :ayudantias, :estudiante_id, :int
  end
end
