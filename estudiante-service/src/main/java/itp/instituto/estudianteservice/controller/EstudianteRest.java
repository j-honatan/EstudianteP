package itp.instituto.estudianteservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import  itp.instituto.estudianteservice.entity.Estudiante;
import  itp.instituto.estudianteservice.entity.Curso;
import itp.instituto.estudianteservice.service.EstudianteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/estudiante")
public class EstudianteRest {

    @Autowired
    EstudianteService estudianteService;

    // -------------------Retrieve All Estudiantes--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Estudiante>> listAllEstudiantes(@RequestParam(name = "cursoId" , required = false) Long cursoId ) {
        List<Estudiante> estudiantes =  new ArrayList<>();
        if (null ==  cursoId) {
            estudiantes = estudianteService.findEstudianteAll();
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            Curso Curso= new Curso();
            Curso.setId(cursoId);
            estudiantes = estudianteService.findEstudiantesByCurso(Curso);
            if ( null == estudiantes ) {
                log.error("Estudiantes with Curso id {} not found.", cursoId);
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(estudiantes);
    }

    // -------------------Retrieve Single Estudiante------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable("id") long id) {
        log.info("Fetching Estudiante with id {}", id);
        Estudiante estudiante = estudianteService.getEstudiante(id);
        if (  null == estudiante) {
            log.error("Estudiante with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(estudiante);
    }

    // -------------------Create a Estudiante-------------------------------------------

    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante( @RequestBody Estudiante estudiante, BindingResult result) {
        log.info("Creating Estudiante : {}", estudiante);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Estudiante estudianteDB = estudianteService.createEstudiante (estudiante);

        return  ResponseEntity.status( HttpStatus.CREATED).body(estudianteDB);
    }

    // ------------------- Update a Estudiante ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEstudiante(@PathVariable("id") long id, @RequestBody Estudiante estudiante) {
        log.info("Updating Estudiante with id {}", id);

        Estudiante currentEstudiante = estudianteService.getEstudiante(id);

        if ( null == currentEstudiante ) {
            log.error("Unable to update. Estudiante with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        estudiante.setId(id);
        currentEstudiante=estudianteService.updateEstudiante(estudiante);
        return  ResponseEntity.ok(currentEstudiante);
    }

    // ------------------- Delete a Estudiante-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Estudiante> deleteEstudiante(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Estudiante with id {}", id);

        Estudiante estudiante = estudianteService.getEstudiante(id);
        if ( null == estudiante ) {
            log.error("Unable to delete. Estudiante with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        estudiante = estudianteService.deleteEstudiante(estudiante);
        return  ResponseEntity.ok(estudiante);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
