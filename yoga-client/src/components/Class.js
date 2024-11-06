import { useEffect,useState, useContext } from "react";
import EnrollButton from "./EnrollButton";
import AuthContext from "../contexts/AuthContext";
import ValidationSummary from "./ValidationSummary";
import { formattedTime } from "../services/dateUtils";
import { getEnrollmentCount } from "../services/sessionService";
import { useNavigate } from "react-router-dom";

export default function Class({cls}){

const { appUser } = useContext(AuthContext);
const navigate = useNavigate(); 

const [enrolled, setEnrolled] = useState(0);

const [errors, setError] = useState(0);

const handleEditClick =(id)=>{
  navigate('/ClassForm', {state: {sessionId: id}});
}
const handleErrors = (errors) => {

setError(errors);

}

console.log("appUser:", appUser);
console.log("Instructor:", cls.instructor);

return (

<li>
  <ValidationSummary />
<span>
    {formattedTime(cls.start)} - {formattedTime(cls.end)}, {cls.instructor.firstName} {cls.instructor.lastName}, {cls.location.name} {cls.enrollmentCount}/{cls.capacity}
    </span>
    {appUser && cls.instructor.appUserId===appUser.id ? (<button onClick={()=>handleEditClick(cls.id)}>edit</button>) : (<EnrollButton cls={cls} onError={handleErrors}/>)}
</li>

)



}