# -*- coding: utf-8 -*-
module EstudiantesHelper
  
  #Método auxiliar para saber si es administrador o no un estudiante.
  def es_admin
    redirect_to(root_url) unless estudiante_actual.admin?
  end
  
  
end
