
import { useEffect, useState, useContext } from "react";
import Class from "./Class";
import { fetchSessionsByDate } from "../services/sessionService";

import { formatDate } from "../services/dateUtils"; 

function ClassList({ selectedDate }) {
  
  const [classes, setClasses] = useState([]);
  


    useEffect(() => {

      
  
      setClasses(fetchSessionsByDate(selectedDate));
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