package itp.instituto.estudianteservice.service;

import itp.instituto.estudianteservice.entity.Curso;
import itp.instituto.estudianteservice.entity.Estudiante;

import java.util.List;

public interface EstudianteService {
    public List<Estudiante> findEstudianteAll();
    public List<Estudiante> findEstudiantesByCurso(Curso curso);

    public Estudiante createEstudiante(Estudiante estudiante);
    public Estudiante updateEstudiante(Estudiante estudiante);
    public Estudiante deleteEstudiante(Estudiante estudiante);
    public  Estudiante getEstudiante(Long id);
}
