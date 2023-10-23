package learnb.yoga.domain;

import learnb.yoga.data.LocationRepository;
import learnb.yoga.models.Location;
import learnb.yoga.validation.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> findAll(){

        return repository.findAll();
    }

    public Location findById(int id){
        return repository.findById(id);
    }

    public Result<Location> add(Location location) {

        Result<Location> result = validate(location);

        if(!result.isSuccess()){
            return result;
        }

        location = repository.add(location);
        result.setPayload(location);

        return result;
    }

    public Result<Location> update(Location location) {

        Result<Location> result = validate(location);

        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.update(location);
        if(!success) {

            String msg = String.format(
                    "could not update location id %s", location.getId());
            result.addMessage(ActionStatus.INVALID,msg);

        }
    result.setPayload(location);
        return result;
    }

    public Result<Location> deleteById(int id){

        Result<Location> result = new Result<>();
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            result.addMessage(ActionStatus.NOT_FOUND, "location id `" + id + "` not found.");
        }
        return result;
    }




    private Result<Location> validate(Location location) {

        Result<Location> result = new Result();

        if(location == null){
            result.addMessage(ActionStatus.INVALID, "location cannot be null");
        }
        if(location.getName()==null||location.getName().isEmpty()||location.getName().isBlank()){

            result.addMessage(ActionStatus.INVALID, "name cannot be blank");
        }

        List<Location> locations = repository.findAll();


        for(Location l : locations){
            if(l.getName().toLowerCase() == location.getName().toLowerCase() &&
                    (l.getId() != location.getId() && location.getId() != 0)){
                result.addMessage(ActionStatus.DUPLICATE, "location name must be unique");
            }
        }

        return result;

    }
}
