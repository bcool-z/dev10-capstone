package learnb.yoga.domain;

import learnb.yoga.data.SessionJdbcTemplateRepository;
import learnb.yoga.data.SessionRepository;
import learnb.yoga.models.Session;
import learnb.yoga.validation.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SessionService {

private final SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

public Session findById(int id){
        return repository.findById(id);
}

public Result<Session> add(Session session){

Result<Session> result = validate(session);

if(!result.isSuccess()){
    return result;
}

if(session.getId()!=0){
    result.addMessage(ActionStatus.INVALID, "session id cannot be set for add operation");
    return result;
}
if(result.isSuccess()){

    Session s = repository.add(session);
    result.setPayload(s);
}
return result;
}

public Result<Session> update(Session session){

        Result<Session> result = validate(session);
        if(!result.isSuccess()){
            return result;
        }
        if(session.getId() <= 0){
            result.addMessage(ActionStatus.INVALID, "session id is required");
            return result;
        }

        boolean success = repository.update(session);
        if (!success){
            result.addMessage(ActionStatus.INVALID, "could not update session id "+ session.getId());
        }

        return result;

}

public Result<Session> deleteById(int id) {

        Result<Session> result = new Result<>();
        boolean success = repository.deleteById(id);
        if(!success) {

            result.addMessage(ActionStatus.NOT_FOUND, "could not delete session id " + id);
        }
        return result;
}

private Result<Session> validate(Session session){
        Result<Session> result = new Result<>();

        if(session == null){
            result.addMessage(ActionStatus.INVALID, "Session cannot be null.");
            return result;
        }
        if (session.getInstructor() == null){
            result.addMessage(ActionStatus.INVALID, "Instructor cannot be null.");
            return result;
        }
        if(session.getLocation() == null){

            result.addMessage(ActionStatus.INVALID, "Location cannot be null.");
            return result;

        }
        if(session.getCapacity() <= 0){
            result.addMessage(ActionStatus.INVALID, "Capacity must be positive integer");
            return result;
        }
        if(session.getStart().isBefore(LocalDateTime.now())){
            result.addMessage(ActionStatus.INVALID, "Session start time must be future date and time");
            return result;
        }
        if(session.getEnd().isBefore(session.getStart())){
            result.addMessage(ActionStatus.INVALID, "Session end time must be set for after start time");
            return result;
        }

            List<Session> sessions1 = repository.findByDate(session.getStart().toLocalDate());
            List<Session> sessions2 = repository.findByDate(session.getEnd().toLocalDate());

            List<Session> sessions = Stream.concat(sessions1.stream(), sessions2.stream())
                    .distinct()
                    .toList();


        for(Session s : sessions){
            boolean overlaps = s.getStart().isBefore(session.getEnd())
                    && s.getEnd().isAfter(session.getStart());

            if(s.getId() != session.getId()){
                if(s.getLocation().getId() == session.getLocation().getId()){
                    result.addMessage(ActionStatus.INVALID, "session location overlap");
                    return result;
                }
                if(s.getInstructor().getAppUserId() == session.getInstructor().getAppUserId()){
                    result.addMessage(ActionStatus.INVALID, "session instructor overlap");
                    return result;
                }
            }


        }


return result;

}

}
