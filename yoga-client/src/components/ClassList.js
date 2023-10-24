
import { useEffect, useState, useContext } from "react";
import Class from "./Class";

function ClassList({ selectedDate }) {
  
  const [classes, setClasses] = useState([]);
  
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Add 1 to the month because it is 0-based
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;};

    useEffect(() => {
      // Define a function to fetch classes based on the selected date
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
  
      // Call the fetchClasses function whenever selectedDate changes
      fetchClasses();
    }, [selectedDate]);

  // const filteredClasses = classes.filter((cls) => cls.date === selectedDate);

  return (
    <div>
      <h2>Classes on {selectedDate.toDateString()}</h2>
      {classes.length === 0 ? (
        <p></p>
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