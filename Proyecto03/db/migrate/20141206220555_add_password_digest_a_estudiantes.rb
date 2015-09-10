class AddPasswordDigestAEstudiantes < ActiveRecord::Migration
  def change
    add_column :estudiantes, :password_digest, :string
  end
end
