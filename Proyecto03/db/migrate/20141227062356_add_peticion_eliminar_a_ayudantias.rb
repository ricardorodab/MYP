class AddPeticionEliminarAAyudantias < ActiveRecord::Migration
  def change
    add_column :ayudantias, :peticion_eliminar, :boolean, default: false
  end
end
