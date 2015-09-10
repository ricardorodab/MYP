class AgregaActivacionAMaestros < ActiveRecord::Migration
  def change
  	add_column :maestros, :activacion_digest, :string
    add_column :maestros, :activado, :boolean, default: false
    add_column :maestros, :activado_a, :datetime
  end
end
