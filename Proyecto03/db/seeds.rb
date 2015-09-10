# -*- coding: utf-8 -*-
# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)
Estudiante.create!(nombre:  "José Ricardo Rodríguez Abreu",
                   correo: "ricardo_rodab@ciencias.unam.mx",
                   password:              "123456",
                   password_confirmation: "123456",
                   admin: true,
                   activado: true,
                   activado_a: Time.zone.now)

Estudiante.create!(nombre:  "Jorge Rafael Cruz Franco",
                   correo: "rafacf128@ciencias.unam.mx",
                   password:              "123456",
                   password_confirmation: "123456",
                   admin: true,
                   activado: true,
                   activado_a: Time.zone.now)

Estudiante.create!(nombre:  "Luis Rodrigo Rojo Morales",
                   correo: "rodrigorojo@ciencias.unam.mx",
                   password:              "123456",
                   password_confirmation: "123456",
                   admin: true,
                   activado: true,
                   activado_a: Time.zone.now)
Estudiante.create!(nombre:  "Estudiante Regular",
                   correo: "er@ciencias.unam.mx",
                   password:              "123456",
                   password_confirmation: "123456",
                   admin: false,
                   activado: true,
                   activado_a: Time.zone.now)

99.times do |n|
  nombre  = Faker::Name.name
  correo = "alumno-#{n+1}@ciencias.unam.mx"
  password = "password"
  Estudiante.create!(nombre:  nombre,
                     correo: correo,
                     password:              password,
                     password_confirmation: password,
                     activado: true,
                     activado_a: Time.zone.now)
end

Maestro.create!(nombre:  "Canek Pelaez Valdés",
                correo: "canek@ciencias.unam.mx",
                password:              "123456",
                password_confirmation: "123456",
                admin: false,
                grado: "Doctor",                
                aprobado: true)
Maestro.create!(nombre:  "Laura Pastrana Ramírez",
                correo: "laura@ciencias.unam.mx",
                password:              "123456",
                password_confirmation: "123456",
                admin: false,
                grado: "Doctor",                
                aprobado: false)
Maestro.create!(nombre:  "Chuck Norris",
                correo: "chuck@ciencias.unam.mx",
                password:              "123456",
                password_confirmation: "123456",
                admin: false,
                grado: "Doctor",                
                aprobado: true)

20.times do |n|
  nombre = Faker::Name.name
  correo = "profesor-#{n+1}@ciencias.unam.mx"
  password = "password"
  Maestro.create!(nombre: nombre,
                  correo: correo,
                  password: password,
                  password_confirmation: password,
                  aprobado: true)
end

estudiantes = Estudiante.order(:activado_a).take(20)
20.times do |n| 
  1.times do
    content = Faker::Lorem.sentence(5)
    estudiantes.each { |estudiante| 
      estudiante.comentarios.create!(comentario: content,
                                     maestro_id: n+1,
                                     semestre: Time.now.to_date,
                                     materia: Faker::Lorem.sentence(2), 
                                     facilidad: 10,
                                     ayuda: 10,
                                     claridad: 10,
                                     aprobo: true,
                                     recursador: true,
                                     evaluacion: Faker::Lorem.sentence(3),
                                     recomendacion: true) }
  end
end
