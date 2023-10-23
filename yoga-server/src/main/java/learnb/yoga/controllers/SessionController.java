package learnb.yoga.controllers;

import learnb.yoga.domain.SessionService;
import learnb.yoga.models.Session;
import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

   private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/{index}")
    public ResponseEntity<Session> findById(@PathVariable int index){
Session session = service.findById(index);
if(session==null){
    return ResponseEntity.notFound().build();
}
return ResponseEntity.ok(session);


    }

    @PostMapping
    public ResponseEntity<Session> add(@RequestBody Session session){

        Result<Session> result = service.add(session);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/{index}")
    public ResponseEntity<Session> update(@PathVariable int index, @RequestBody Session session){

        if(session != null && session.getId() != index){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Session> result = service.update(session);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> delete(@PathVariable int index){
        Result<Session> result = service.deleteById(index);
        return  new ResponseEntity<>(getStatus(result,HttpStatus.NO_CONTENT));

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
