package itp.instituto.estudianteservice.repository;


import itp.instituto.estudianteservice.entity.Estudiante;
import itp.instituto.estudianteservice.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.List;
public interface EstudianteRepository extends JpaRepository<Estudiante,Long>{
    public Estudiante findByNumberID(String numberID);
    public List<Estudiante> findByLastName(String lastName);
    public List<Estudiante> findByCurso(Curso curso);
}
