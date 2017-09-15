# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20141229090747) do

  create_table "ayudantias", force: :cascade do |t|
    t.integer  "maestro_id",        limit: 4
    t.datetime "created_at",                                  null: false
    t.datetime "updated_at",                                  null: false
    t.integer  "estudiante_id",     limit: 4
    t.boolean  "peticion_eliminar", limit: 1, default: false
  end

  add_index "ayudantias", ["maestro_id"], name: "index_ayudantias_on_maestro_id", using: :btree

  create_table "comentarios", force: :cascade do |t|
    t.text     "comentario",          limit: 65535
    t.integer  "estudiante_id",       limit: 4
    t.integer  "maestro_id",          limit: 4
    t.datetime "created_at",                                        null: false
    t.datetime "updated_at",                                        null: false
    t.integer  "maestros_id",         limit: 4
    t.date     "semestre"
    t.string   "materia",             limit: 255
    t.integer  "facilidad",           limit: 4
    t.integer  "ayuda",               limit: 4
    t.integer  "claridad",            limit: 4
    t.boolean  "aprobo",              limit: 1,     default: false
    t.boolean  "recursador",          limit: 1,     default: false
    t.text     "evaluacion",          limit: 65535
    t.boolean  "recomendacion",       limit: 1,     default: false
    t.boolean  "warning",             limit: 1,     default: false
    t.string   "ayudante_nombre",     limit: 255
    t.text     "ayudante_comentario", limit: 65535
    t.text     "respuesta",           limit: 65535
  end

  add_index "comentarios", ["estudiante_id"], name: "index_comentarios_on_estudiante_id", using: :btree
  add_index "comentarios", ["maestro_id"], name: "index_comentarios_on_maestro_id", using: :btree
  add_index "comentarios", ["maestros_id"], name: "index_comentarios_on_maestros_id", using: :btree

  create_table "estudiantes", force: :cascade do |t|
    t.string   "nombre",            limit: 255
    t.string   "correo",            limit: 255
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "password_digest",   limit: 255
    t.string   "remember_digest",   limit: 255
    t.boolean  "admin",             limit: 1,   default: false
    t.string   "activacion_digest", limit: 255
    t.boolean  "activado",          limit: 1,   default: false
    t.datetime "activado_a"
    t.boolean  "ayudante",          limit: 1,   default: false
  end

  add_index "estudiantes", ["correo"], name: "index_estudiantes_on_correo", unique: true, using: :btree

  create_table "maestros", force: :cascade do |t|
    t.string   "nombre",            limit: 255
    t.string   "correo",            limit: 255
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "password_digest",   limit: 255
    t.boolean  "admin",             limit: 1,   default: false
    t.string   "grado",             limit: 255
    t.boolean  "aprobado",          limit: 1,   default: false
    t.string   "remember_digest",   limit: 255
    t.boolean  "correo_enviado",    limit: 1,   default: false
    t.boolean  "registrado",        limit: 1,   default: false
    t.string   "activacion_digest", limit: 255
    t.boolean  "activado",          limit: 1,   default: false
    t.datetime "activado_a"
  end

  add_index "maestros", ["correo"], name: "index_maestros_on_correo", unique: true, using: :btree

  create_table "simple_captcha_data", force: :cascade do |t|
    t.string   "key",        limit: 40
    t.string   "value",      limit: 6
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "simple_captcha_data", ["key"], name: "idx_key", using: :btree

  add_foreign_key "ayudantias", "maestros"
  add_foreign_key "comentarios", "estudiantes"
  add_foreign_key "comentarios", "maestros"
end
