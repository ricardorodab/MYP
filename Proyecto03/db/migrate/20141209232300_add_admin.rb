class AddAdmin < ActiveRecord::Migration
  def change
    add_column :maestros, :admin, :boolean, default: false
  end
end
