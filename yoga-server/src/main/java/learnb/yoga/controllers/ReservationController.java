package learnb.yoga.controllers;

import learnb.yoga.domain.ReservationService;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.Reservation;
import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

@GetMapping("/{index}")
    public ResponseEntity<Reservation> findById(@PathVariable int index) {

        Reservation reservation = service.findById(index);
        if (reservation == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
}

@GetMapping("/student/{index}")
public List<Reservation> findByStudent(@PathVariable int index) {
    AppUser student = new AppUser();
    student.setAppUserId(index);

    return service.findByStudent(student);
}

@PostMapping
    public ResponseEntity<Reservation> add(@RequestBody Reservation reservation){
    Result<Reservation> result = service.add(reservation);
    return new ResponseEntity<>(result.getPayload(),getStatus(result,HttpStatus.CREATED));
}

@PutMapping("/{index}")
public ResponseEntity<Reservation> update(@PathVariable int index,
                                          @RequestBody Reservation reservation){

        if(reservation != null && reservation.getId() != index){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Reservation> result = service.update(reservation);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
}

    private HttpStatus getStatus(Result<?> result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }

}
