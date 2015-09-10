class AddAdminEstudiantes < ActiveRecord::Migration
  def change
    add_column :estudiantes, :admin, :boolean, default: false
  end
end
