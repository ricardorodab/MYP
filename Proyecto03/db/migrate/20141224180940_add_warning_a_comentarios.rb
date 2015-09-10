class AddWarningAComentarios < ActiveRecord::Migration
  def change
    add_column :comentarios, :warning, :boolean, default: false 
  end
end
