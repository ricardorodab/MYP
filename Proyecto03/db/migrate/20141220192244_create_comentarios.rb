class CreateComentarios < ActiveRecord::Migration
  def change
    create_table :comentarios do |t|
      t.text :comentario
      t.references :estudiante, index: true
      t.references :maestro, index: true
      t.timestamps null: false
    end
    add_reference :comentarios, :maestros, index: true
    add_foreign_key :comentarios, :estudiantes
    add_foreign_key :comentarios, :maestros

    #add_index :comentarios, [:estudiante_id, :creado_a]
    #add_index :comentarios, [:maestro_id, :creado_a]
  end
end
