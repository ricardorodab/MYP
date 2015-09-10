class AddAyudanteAComentarios < ActiveRecord::Migration
  def change
    add_column :comentarios, :ayudante_nombre, :string
    add_column :comentarios, :ayudante_comentario, :text
  end
end
