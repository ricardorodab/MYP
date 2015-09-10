class AddRespuestaAComentarios < ActiveRecord::Migration
  def change
    add_column :comentarios, :respuesta, :text
  end
end
