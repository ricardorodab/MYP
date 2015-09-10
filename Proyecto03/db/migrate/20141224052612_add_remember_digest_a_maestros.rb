class AddRememberDigestAMaestros < ActiveRecord::Migration
  def change
    add_column :maestros, :remember_digest, :string
  end
end
