class CreateMaestros < ActiveRecord::Migration
  def change
    create_table :maestros do |t|
      t.string :nombre
      t.string :correo

      t.timestamps
    end
  end
end
