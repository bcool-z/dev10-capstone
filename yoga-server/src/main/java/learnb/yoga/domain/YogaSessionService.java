package learnb.yoga.domain;

import learnb.yoga.data.YogaSessionRepository;
import learnb.yoga.models.YogaSession;
import learnb.yoga.validation.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class YogaSessionService {

private final YogaSessionRepository repository;

    public YogaSessionService(YogaSessionRepository repository) {
        this.repository = repository;
    }

    public int getEnrolled(int sessionId){return repository.getEnrollmentCount(sessionId);}
public YogaSession findById(int id){
        return repository.findById(id);
}

public List<YogaSession> findByDate(LocalDate date){

        return repository.findByDate(date);
}

public Result<YogaSession> add(YogaSession session){

Result<YogaSession> result = validate(session);

if(!result.isSuccess()){
    return result;
}

if(session.getId()!=0){
    result.addMessage(ActionStatus.INVALID, "session id cannot be set for add operation");
    return result;
}
if(result.isSuccess()){

    YogaSession s = repository.add(session);
    result.setPayload(s);
}
return result;
}

public Result<YogaSession> update(YogaSession session){

        Result<YogaSession> result = validate(session);
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

public Result<YogaSession> deleteById(int id) {

        Result<YogaSession> result = new Result<>();
        boolean success = repository.deleteById(id);
        if(!success) {

            result.addMessage(ActionStatus.NOT_FOUND, "could not delete session id " + id);
        }
        return result;
}

private Result<YogaSession> validate(YogaSession session){
        Result<YogaSession> result = new Result<>();

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

            List<YogaSession> sessions1 = repository.findByDate(session.getStart().toLocalDate());
            List<YogaSession> sessions2 = repository.findByDate(session.getEnd().toLocalDate());

            List<YogaSession> sessions = Stream.concat(sessions1.stream(), sessions2.stream())
                    .distinct()
                    .toList();


        for(YogaSession s : sessions){
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
