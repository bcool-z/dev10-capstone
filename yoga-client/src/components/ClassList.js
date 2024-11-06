
import { useEffect, useState, useContext } from "react";
import Class from "./Class";
import { getEnrollmentCount, fetchSessionsByDate } from "../services/sessionService";

import { formatDate } from "../services/dateUtils"; 

function ClassList({ selectedDate }) {
  
  const [classes, setClasses] = useState([]);
  


    useEffect(() => {
      const fetchData = async () => {
      const sessions = await fetchSessionsByDate(formatDate(selectedDate));

    const sessionsWithEnollment = await Promise.all(

      (sessions || []).map(async (session) => {

        const enrollmentCount = await getEnrollmentCount(session.id);
          return {...session, enrolled: (enrollmentCount||0)};
      })

    )

      setClasses(sessionsWithEnollment);}
        fetchData();
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