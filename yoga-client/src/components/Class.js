import { useEffect,useState, useContext } from "react";
import EnrollButton from "./EnrollButton";
import AuthContext from "../contexts/AuthContext";
import ValidationSummary from "./ValidationSummary";
import { formattedTime } from "../services/dateUtils";

export default function Class({cls}){

  const { user } = useContext(AuthContext);

const [enrolled, setEnrolled] = useState(0);

const [errors, setError] = useState([]);

const handleErrors = (errors) => {

setError(errors);

}

useEffect(() => {
    // Define a function to fetch classes based on the selected date
    const fetchEnrolled = async () => {
      try {
        const response = await fetch(`http://localhost:8080/session/count/${cls.id}`);
        if (response.ok) {
          const data = await response.json();
          setEnrolled(data);
        } else {
          console.error('Error fetching classes');
        }
      } catch (error) {
        console.error('Error fetching classes:', error);
      }
    };

    // Call the fetchClasses function whenever selectedDate changes
    fetchEnrolled();
  }, []);





return (

<li>
  <ValidationSummary />
    {formattedTime(cls.start)} - {formattedTime(cls.end)}, {cls.instructor.firstName} {cls.instructor.lastName}, {cls.location.name} {enrolled}/{cls.capacity} <EnrollButton cls={cls} onError={handleErrors} />
</li>

)



}