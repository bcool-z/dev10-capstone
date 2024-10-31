package learnb.yoga.controllers;

import learnb.yoga.domain.YogaSessionService;
import learnb.yoga.models.YogaSession;
import learnb.yoga.validation.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static learnb.yoga.controllers.ControllerHelper.getStatus;

@RestController
@RequestMapping("/session")
public class YogaSessionController {

   private final YogaSessionService service;

    public YogaSessionController(YogaSessionService service) {
        this.service = service;
    }

    @GetMapping("/count/{index}")
    public int getEnrolled(@PathVariable int index) {return service.getEnrolled(index);}

    @GetMapping("/{index}")
    public ResponseEntity<YogaSession> findById(@PathVariable int index) {
        YogaSession yogaSession = service.findById(index);
        if (yogaSession == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(yogaSession);
    }

    @GetMapping("/date/{date}")
public List<YogaSession> findByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        return service.findByDate(date);
    }

    @PostMapping
    public ResponseEntity<YogaSession> add(@RequestBody YogaSession yogaSession){

        Result<YogaSession> result = service.add(yogaSession);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/{index}")
    public ResponseEntity<YogaSession> update(@PathVariable int index, @RequestBody YogaSession yogaSession){

        if(yogaSession != null && yogaSession.getId() != index){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<YogaSession> result = service.update(yogaSession);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> delete(@PathVariable int index){
        Result<YogaSession> result = service.deleteById(index);
        return  new ResponseEntity<>(getStatus(result,HttpStatus.NO_CONTENT));

    }

   /* private HttpStatus getStatus(Result<?> result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }*/

}
