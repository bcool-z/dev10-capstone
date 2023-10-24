package learnb.yoga.controllers;

import learnb.yoga.domain.ActionStatus;
import learnb.yoga.domain.LocationService;
import learnb.yoga.models.Location;
import learnb.yoga.models.Reservation;
import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static learnb.yoga.controllers.ControllerHelper.getStatus;

@RestController
@RequestMapping("/location")
public class LocationController {

private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> findAll() {return service.findAll();}

    @GetMapping("/{index}")
    public ResponseEntity<Location> findById(@PathVariable int index) {
        Location location = service.findById(index);
        if(location == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Location location){

        if(location.getId() != 0){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Result<Location> result = service.add(location);
        if(!result.isSuccess()){
            return new ResponseEntity<>(result.getMessages(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(),HttpStatus.CREATED);

    }

    @PutMapping("/index")
    public ResponseEntity<?> update(@PathVariable int index, @RequestBody Location location) {

        if(index != location.getId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(location.getId() <= 0){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Result<Location> result= service.update(location);
        if (!result.isSuccess()) {
            if (result.getStatus() == ActionStatus.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);

    }

    @DeleteMapping("{index}")
    public ResponseEntity<Void> deleteById(@PathVariable int index){
        Result<Location> result = service.deleteById(index);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

//    private HttpStatus getStatus(Result<?> result, HttpStatus statusDefault) {
//        switch (result.getStatus()) {
//            case INVALID:
//                return HttpStatus.PRECONDITION_FAILED;
//            case DUPLICATE:
//                return HttpStatus.FORBIDDEN;
//            case NOT_FOUND:
//                return HttpStatus.NOT_FOUND;
//        }
//        return statusDefault;
//    }

}
