class AgregaActivacionAEstudiantes < ActiveRecord::Migration
  def change
    add_column :estudiantes, :activacion_digest, :string
    add_column :estudiantes, :activado, :boolean, default: false
    add_column :estudiantes, :activado_a, :datetime
  end
end
