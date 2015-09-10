class AddRememberDigestAEstudiantes < ActiveRecord::Migration
  def change
    add_column :estudiantes, :remember_digest, :string
  end
end
