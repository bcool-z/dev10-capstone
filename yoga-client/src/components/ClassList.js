
import { useEffect, useState, useContext } from "react";
import Class from "./Class";

import { formatDate } from "../services/dateUtils"; 

function ClassList({ selectedDate }) {
  
  const [classes, setClasses] = useState([]);
  


    useEffect(() => {

      const fetchClasses = async () => {
        try {
          const response = await fetch(`http://localhost:8080/session/date/${formatDate(selectedDate)}`);
          if (response.ok) {
            const data = await response.json();
            setClasses(data);
          } else {
            console.error('Error fetching classes');
          }
        } catch (error) {
          console.error('Error fetching classes:', error);
        }
      };
  
      fetchClasses();
    }, [selectedDate]);

  

  return (
    <div>
      <h2>Classes on {selectedDate.toDateString()}</h2>
      {classes.length === 0 ? (
        <p>No Classes Scheduled</p>
      ) : (
        <ul>
          {classes.map((cls) => (
            <Class key={cls.id} cls={cls} />
          ))}
        </ul>
      )}
    </div>
  );
}

export default ClassList;