package itp.instituto.estudianteservice.service;

import itp.instituto.estudianteservice.entity.Curso;
import itp.instituto.estudianteservice.entity.Estudiante;
import itp.instituto.estudianteservice.repository.EstudianteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class EstudianteServiceImpl implements EstudianteService{

    @Autowired
    EstudianteRepository estudianteRepository;

    @Override
    public List<Estudiante> findEstudianteAll() {
        return estudianteRepository.findAll();
    }

    @Override
    public List<Estudiante> findEstudiantesByCurso(Curso curso) {
        return estudianteRepository.findByCurso(curso);
    }

    @Override
    public Estudiante createEstudiante(Estudiante estudiante) {

        Estudiante estudianteDB = estudianteRepository.findByNumberID ( estudiante.getNumberID () );
        if (estudianteDB != null){
            return  estudianteDB;
        }

        estudiante.setState("CREATED");
        estudianteDB = estudianteRepository.save ( estudiante );
        return estudianteDB;
    }

    @Override
    public Estudiante updateEstudiante(Estudiante estudiante) {
        Estudiante estudianteDB = getEstudiante(estudiante.getId());
        if (estudianteDB == null){
            return  null;
        }
        estudianteDB.setFirstName(estudiante.getFirstName());
        estudianteDB.setLastName(estudiante.getLastName());
        estudianteDB.setEmail(estudiante.getEmail());
        estudianteDB.setPhotoUrl(estudiante.getPhotoUrl());

        return  estudianteRepository.save(estudianteDB);
    }

    @Override
    public Estudiante deleteEstudiante(Estudiante estudiante) {
        Estudiante estudianteDB = getEstudiante(estudiante.getId());
        if (estudianteDB ==null){
            return  null;
        }
        estudiante.setState("DELETED");
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante getEstudiante(Long id) {
        return  estudianteRepository.findById(id).orElse(null);
    }
}