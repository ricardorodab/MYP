class AddColumnasComentariosMaestros < ActiveRecord::Migration
  def change
    add_column :comentarios, :semestre, :date
    add_column :comentarios, :materia, :string
    add_column :comentarios, :facilidad, :int
    add_column :comentarios, :ayuda, :int
    add_column :comentarios, :claridad, :int
    add_column :comentarios, :aprobo, :boolean, default: false
    add_column :comentarios, :recursador, :boolean, default: false
    add_column :comentarios, :evaluacion, :text
    add_column :comentarios, :recomendacion, :boolean, default: false
  end
end
