class AddPasswordDigestToMaestros < ActiveRecord::Migration
  def change
    add_column :maestros, :password_digest, :string
  end
end
